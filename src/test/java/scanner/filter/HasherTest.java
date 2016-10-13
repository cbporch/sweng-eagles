package scanner.filter;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 */
public class HasherTest {

    @Test
    /**
     * This will test to see if the words are being hashed
     */
    public void testHash() throws Exception {
        ArrayList<String> inputList = new ArrayList<String>();
        inputList.add("The");
        inputList.add("dog");
        inputList.add("barked");
        Hasher hasher = new Hasher();
        ArrayList<String> outputList = hasher.hash(inputList);
        assertEquals("Hashed the", outputList.get(0));
        assertEquals("Hashed dog", outputList.get(1));
        assertEquals("Hashed barked", outputList.get(2));
    }

}