package scanner.pandoc;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.swing.*;

/**
 * Created by dsmith on 12/06/2016.
 */
public class Pandoc {

    public static void main(String[] args){

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

            Channel channel=session.openChannel("shell");

            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            channel.connect();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
