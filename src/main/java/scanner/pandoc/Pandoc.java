package scanner.pandoc;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.Random;

/**
 * Created by dsmith on 12/06/2016.
 */
public class Pandoc {

    //Specifies what directory files get transferd to on the server
    String workingDir = "/pandoc/tmp/";

    public Pandoc()
    {}

    /**
     * Constructor to use custom working directory
     * @param path
     */
    public Pandoc(String path)
    {
        workingDir = path;
    }

    /**
     * Runs a file through pandoc to convert it to a text file
     *
     * See Pandoc documentation for supported conversions
     *
     * Does not support pdf to txt
     * Does not support doc to txt
     * Does support docx to txt
     *
     * @param localPath
     * @param localFilename
     * @return String of the text file
     */
    public String convertFile(String localPath, String localFilename){

        // Generate a random number for the file name for this instance
        Random rand = new Random();
        String remoteName = Integer.toString(rand.nextInt(3000))+".txt";

        try{
            // Initiate a JSch instance
            JSch jsch=new JSch();

            // Get the url for the private key for the server
            String uri = Pandoc.class.getClass().getResource("/ASRC-Pandoc.pem").getPath().replace("%20", " ");
            jsch.addIdentity(uri);

            String user="ubuntu";
            String host = "ec2-54-145-184-115.compute-1.amazonaws.com";

            Session session=jsch.getSession(user, host, 22);

            /* Insecure solution */
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            /* End Insecure solution */

            session.connect();

            // Uploads file to pandoc server to be converted to a txt file
            upload(session, localPath);

            // Runs conversion commands on the file to get a txt file result
            String output = toText(session, workingDir+localFilename, workingDir+remoteName);

            // Deletes files after processing
            delete(session,localFilename);
            delete(session,remoteName);

            session.disconnect();

            return output;
        }
        catch(Exception e){
            System.out.println(e);
        }

        // Return null if there is a problem
        return null;
    }

    /**
     * Executes commands via an ssh tunnel
     *
     * @param session
     * @param command
     * @return
     * @throws IOException
     */
    private Channel openExecChannel(Session session,String command) throws IOException {
        try {
            ChannelExec channel=(ChannelExec)session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();
            return channel;
        }
        catch ( JSchException e) {
            throw new IOException("could not open exec channel with command " + command,e);
        }
    }

    /**
     * Uploads a file to the server
     *
     * @param session
     * @param path
     */
    private void upload(Session session, String path) {
        try {

            ChannelSftp channelSftp = (ChannelSftp)session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(workingDir);

            File f1 = new File(path);
            channelSftp.put(new FileInputStream(f1), f1.getName(), ChannelSftp.OVERWRITE);

            channelSftp.exit();
            //session.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads a file from the server
     *
     * @param session
     * @param path
     * @return
     * @throws IOException
     */
    private String read(Session session, String path) throws IOException
    {
        try {
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.cd(workingDir);
            InputStream stream = sftp.get(path);
            String message ="";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                message = org.apache.commons.io.IOUtils.toString(br);
                // read from br
            } finally {
                stream.close();
                sftp.exit();
                return message;

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Deletes a file from the server
     * @param session
     * @param fileName
     * @return
     * @throws IOException
     */
    private void delete(Session session, String fileName) throws IOException
    {
        try {
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.cd(workingDir);
            sftp.rm(fileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Runs pandoc command
     *
     * @param session
     * @param path
     * @param outputPath
     * @return
     * @throws IOException
     */
    private String toText(Session session, String path, String outputPath) throws IOException
    {
        openExecChannel(session, "cd "+workingDir);
        openExecChannel(session, "pandoc -s -o "+outputPath+" "+path);

        String file =  read(session, outputPath);

        return file;
    }

}
