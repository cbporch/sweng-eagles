package scanner.filtering;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Assert;
import sun.misc.BASE64Encoder;

import java.security.PrivateKey;
import java.security.PublicKey;
/**
 * Created by Tom on 11/28/16.
 */
public class EncryptorTest {


    @Test
    public void basicTest()
    {
        //System.out.println("___BASIC TEST___");
        Encryptor e = new Encryptor();
        try
        {
            String message = "Hello World";
            String secret = e.AESencrypt(message);
            assertEquals(secret,"ZHMQORajFKqeQaErHLzLEw==");
            String recovered_message = e.AESdecrypt(secret);
            assertEquals(recovered_message,"Hello World");
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
    }




    @Test
    public void testSingleInput()
    {
        try
        {
            Encryptor e = new Encryptor();
            String testWord = "Hello, my name is Tom, and I am typing this to test the encryption thing. I think it will work, so that is good. If not, I will be sad and probably quit college. bye.";
            testWord += testWord;
            testWord += testWord;
            testWord += testWord;
            testWord += testWord;
            testWord += testWord;
            String encryptedTerm = e.AESencrypt(testWord);
            String decryptedTestWord = e.AESdecrypt(encryptedTerm);
            assertEquals(decryptedTestWord,testWord);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }




    @Test
    public void testListOfWords()
    {
        //testWord is for testing bigger inputs
        String testWord = "Hello, my name is Tom, and I am typing this to test the encryption thing. I think it will work, so that is good. If not, I will be sad and probably quit college. bye.";
        testWord += testWord;
        testWord += testWord;
        testWord += testWord;
        testWord += testWord;
        testWord += testWord;
        Encryptor e = new Encryptor();
        try
        {
            ArrayList<String> words = new ArrayList<>();
            //words.add(testWord);words.add(testWord);words.add(testWord);words.add(testWord);words.add(testWord);
            words.add("The");words.add("dog");words.add("had");words.add("a");words.add("tail");
            ArrayList<String> encryptedWords = e.AESencryptList(words);
            e.AESdecryptList(encryptedWords);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }





    //@Test
    public void getAESKey()
    {
        try {
            Encryptor e = new Encryptor();
        }
        catch (Exception e)
        {
            //System.err.println(e);
        }

    }
}