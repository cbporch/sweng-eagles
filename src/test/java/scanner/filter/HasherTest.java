package scanner.filter;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 */
public class HasherTest {

    @Test
    public void hashString() throws Exception {

    }

    @Test
    public void checkHash() throws Exception {

    }

    @Test
    public void hashSHA() throws Exception {

    }

    @Test
    public void checkSHA() throws Exception {

    }

    @Test
    /**
     * This will test to see if the words are being hashed
     */
    public void testHashArrayList() throws Exception {
        ArrayList<String> inputList = new ArrayList<String>();
        inputList.add("The");
        inputList.add("dog");
        inputList.add("barked");

        ArrayList<String> outputList = Hasher.hashArrayListBCrypt(inputList);
        assertTrue(Hasher.checkHashBCrypt("The", outputList.get(0)));
        assertTrue(Hasher.checkHashBCrypt("dog", outputList.get(1)));
        assertTrue(Hasher.checkHashBCrypt("barked", outputList.get(2)));

        assertFalse(Hasher.checkHashBCrypt("the", outputList.get(0)));
        assertFalse(Hasher.checkHashBCrypt("", outputList.get(1)));
        assertFalse(Hasher.checkHashBCrypt("bark", outputList.get(2)));
    }

}