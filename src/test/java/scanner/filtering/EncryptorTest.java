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
            PublicKey publicKey = e.readPublicKey(publicKeyLocation);
            PrivateKey privateKey = e.readPrivateKey(privateKeyLocation);
            byte[] message = "Hello World".getBytes("UTF8");
            byte[] secret = e.encrypt(publicKey, message);
            byte[] recovered_message = e.decrypt(privateKey, secret);
            System.out.println(new String(recovered_message, "UTF8"));
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
    }





    //******** AES TESTING **********\\

   // @Test
    public void testEncrpytAndDecrypt()
    {
        Encryptor e = new Encryptor();
        String test = e.AESencrypt("Tom is the coolest guy in the world!");
        Assert.assertNotEquals("Tom is the coolest guy in the world!",test);    //Should be encrypted word vs. the original word.
        assertEquals("Tom is the coolest guy in the world!",e.AESdecrypt(test));   //Now it should be decrypted and match.
        assertEquals("xWhtIIral+mT9vK3QdStJu/J8+vhc9vr2T1i58JGd0xsJr7Bp5KV1t//Gmsmqe/6",test);  //encrypted word, matching with expected outcome.
    }

  //  @Test
    public void testEncrpytAndDecryptList()
    {
        Encryptor e = new Encryptor();
        ArrayList<String> origList = new ArrayList<>();
        origList.add("The dog barks.");
        origList.add("The cat meows.");
        origList.add("The Tom complains.");

        ArrayList<String> testList = e.AESencryptList(origList);   //Encrypting the list.
        Assert.assertNotEquals(testList, origList);  //Encrypted list should not equal the original list.
        assertEquals(testList.get(0),"j7684JewP1X0oW8IARDzWg==");   //First word in encrypted list, matching with expected outcome.

        assertEquals(origList, e.AESdecryptList(testList)); //The test list now matches because it was decrypted.
    }
}
