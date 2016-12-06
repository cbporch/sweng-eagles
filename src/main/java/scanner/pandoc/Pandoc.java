package scanner.pandoc;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.*;
import java.util.Random;

/**
 * Created by dsmith on 12/06/2016.
 */
public class Pandoc {

    String workingDir = "/pandoc/tmp/";

    public String convertFile(String localPath, String localFilename){

        Random rand = new Random();
        String remoteName = Integer.toString(rand.nextInt(3000))+".txt";

        try{
            JSch jsch=new JSch();

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose your privatekey(ex. ~/.ssh/id_dsa)");
            chooser.setFileHidingEnabled(false);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose "+
                        chooser.getSelectedFile().getAbsolutePath()+".");
                jsch.addIdentity(chooser.getSelectedFile().getAbsolutePath()
//			 , "passphrase"
                );
            }

            String user="ubuntu";
            String host = "ec2-54-145-184-115.compute-1.amazonaws.com";

            Session session=jsch.getSession(user, host, 22);

            /* Insecure solution */
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            /* End Insecure solution */

            session.connect();

            upload(session, localPath);
            String output = toText(session, workingDir+localFilename, workingDir+remoteName);

            openExecChannel(session, "cd "+workingDir);
            openExecChannel(session, "rm "+localFilename);
            openExecChannel(session, "rm "+remoteName);

            session.disconnect();

            return output;
        }
        catch(Exception e){
            System.out.println(e);
        }

        return null;
    }

    public Channel openExecChannel(Session session,String command) throws IOException {
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

    public void upload(Session session, String path) {
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

    public String read(Session session, String path) throws IOException
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

    public String toText(Session session, String path, String outputPath) throws IOException
    {
        openExecChannel(session, "cd "+workingDir);
        openExecChannel(session, "pandoc -s -o "+outputPath+" "+path);

        String file =  read(session, outputPath);

        return file;

    }

}
