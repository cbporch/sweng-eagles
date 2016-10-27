package scanner.filtering;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 * modified by the same on 10/20/16
 */
public class HasherTest {

    @Test
    public void testHashSHA() throws Exception {
        assertEquals("7iaw3Ur350mqGo7jwQrpkj9hiYB3Lkc/iBml1JQODbJ6wYX4oOHV+E+IvIh/1nsUNzLDBMxfqa2Ob1f1ACio/w==",
                     Hasher.hashSHA("test"));
    }

    @Test
    public void testCheckSHA() throws Exception {
        assertTrue(Hasher.checkSHA("test",
                "7iaw3Ur350mqGo7jwQrpkj9hiYB3Lkc/iBml1JQODbJ6wYX4oOHV+E+IvIh/1nsUNzLDBMxfqa2Ob1f1ACio/w=="));

    }

//    @Test
//    /*
//     * This will test to see if the words are being hashed
//     */
//    public void testHashArrayListBCrypt() throws Exception {
//        ArrayList<String> inputList = new ArrayList<>();
//        inputList.add("The");
//        inputList.add("dog");
//        inputList.add("barked");
//
//        ArrayList<String> outputList = Hasher.hashArrayListBCrypt(inputList);
//        assertTrue(Hasher.checkHashBCrypt("The", outputList.get(0)));
//        assertTrue(Hasher.checkHashBCrypt("dog", outputList.get(1)));
//        assertTrue(Hasher.checkHashBCrypt("barked", outputList.get(2)));
//
//        assertFalse(Hasher.checkHashBCrypt("the", outputList.get(0)));
//        assertFalse(Hasher.checkHashBCrypt("", outputList.get(1)));
//        assertFalse(Hasher.checkHashBCrypt("bark", outputList.get(2)));
//    }

}