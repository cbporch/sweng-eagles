package scanner.filtering;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Assert;

/**
 * Created by Tom on 11/28/16.
 */
public class EncryptorTest {

    @Test
    public void testEncrpytAndDecrypt()
    {
        Encryptor e = new Encryptor();
        String test = e.encrypt("Tom is the coolest guy in the world!");
        Assert.assertNotEquals("Tom is the coolest guy in the world!",test);    //Should be encrypted word vs. the original word.
        assertEquals("Tom is the coolest guy in the world!",e.decrypt(test));   //Now it should be decrypted and match.
        assertEquals("xWhtIIral+mT9vK3QdStJu/J8+vhc9vr2T1i58JGd0xsJr7Bp5KV1t//Gmsmqe/6",test);  //encrypted word, matching with expected outcome.
    }

    @Test
    public void testEncrpytAndDecryptList()
    {
        Encryptor e = new Encryptor();
        ArrayList<String> origList = new ArrayList<>();
        origList.add("The dog barks.");
        origList.add("The cat meows.");
        origList.add("The Tom complains.");

        ArrayList<String> testList = e.encryptList(origList);   //Encrypting the list.
        Assert.assertNotEquals(testList, origList);  //Encrypted list should not equal the original list.
        assertEquals(testList.get(0),"j7684JewP1X0oW8IARDzWg==");   //First word in encrypted list, matching with expected outcome.

        assertEquals(origList, e.decryptList(testList)); //The test list now matches because it was decrypted.
    }
}
