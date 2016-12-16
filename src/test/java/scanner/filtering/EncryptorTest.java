package scanner.filtering;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Assert;
import java.security.PrivateKey;
import java.security.PublicKey;
/**
 * Created by Tom on 11/28/16.
 */
public class EncryptorTest {

    @Test
    public void test()
    {
        Encryptor e = new Encryptor();
        try
        {
            String privateKeyLocation = "src" + File.separatorChar
                    + "main" + File.separatorChar
                    + "java" + File.separator
                    + "scanner" + File.separatorChar
                    + "filtering" + File.separatorChar
                    + "private.der";
            String publicKeyLocation = "src" + File.separatorChar
                    + "main" + File.separatorChar
                    + "java" + File.separator
                    + "scanner" + File.separatorChar
                    + "filtering" + File.separatorChar
                    + "public.der";
            e.readPublicKey(publicKeyLocation);
            e.readPrivateKey(privateKeyLocation);
            byte[] message = "Hello World".getBytes("UTF8");
            //byte[] secret = e.encrypt(message);
           // byte[] recovered_message = e.decrypt(secret);
           // System.out.println(new String(recovered_message, "UTF8"));
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
    }
}
