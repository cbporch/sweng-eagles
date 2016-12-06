package scanner.pandoc;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.swing.*;
import java.io.InputStream;

/**
 * Created by dsmith on 12/06/2016.
 */
public class Pandoc {

    public Pandoc(String host){

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

            Session session=jsch.getSession(user, host, 22);
            
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
