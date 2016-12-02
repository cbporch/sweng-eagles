package scanner.dbEntry;

import org.junit.Assert;

import org.junit.Test;

import java.io.File;

public class CSVFileReaderTest {

    @Test
    public void testCSVFile() throws Exception {
        CSVFileReader cfr = new CSVFileReader();
        String fileLocation = "src" + File.separatorChar
                + "test" + File.separatorChar
                + "java" + File.separator
                + "scanner" + File.separatorChar
                + "dbEntry" + File.separatorChar
                + "test3.csv";
        cfr.interpretCSVFile(fileLocation);

        //__First words input__
        //dog,4,0,
        //is,2,1,
        //the,9,0,

        //Word Testing
        Assert.assertEquals(cfr.wordTestList.get(0).getWord(), "O77ZwQbOrqnh0fhRtJOlJYKub23quBcNpD2jRqhxBiKyHWwsoUxjNr3HcPFhZzvvXtrW5l2GsFvmKBe8kIjZJA==");
        Assert.assertEquals(cfr.wordTestList.get(1).getWord(), "fXdkiI3BPaZDZmbNlwQ6Xq0BRZbNmu9tEvlaVgLRWZO+69RLkgFHMsNAI5580fB8XtQDXRoCFRkW6U1D/6Vc+g==");
        Assert.assertEquals(cfr.wordTestList.get(2).getWord(), "H+rs+lMp5d0cwH8S2tNAb+slfbERoGeD5oV7RJiwvJoo1Rz7MjVKHtY2zZnk0rv8RGaxfBAUdjI2YjTmouiy9g==");
        Assert.assertEquals(cfr.wordTestList.get(0).getRarity(), 4.0, 0.0f);
        Assert.assertEquals(cfr.wordTestList.get(1).getRarity(), 2.0, 0.0f);
        Assert.assertEquals(cfr.wordTestList.get(2).getRarity(), 9.0, 0.0f);
        Assert.assertEquals(cfr.wordTestList.get(0).getNum(), 0);
        Assert.assertEquals(cfr.wordTestList.get(1).getNum(), 1);
        Assert.assertEquals(cfr.wordTestList.get(2).getNum(), 0);

        //__Firt phrases input__
        //the dog is cool,4,2,1,
        //but tom is way cooler,5,3,1,
        //this is line number three,5,5,0,

        //Phrase Testing
        Assert.assertEquals(cfr.phraseTestList.get(0).getPhrase(), "GrJZF9C5/+eOyEcF0mIZnQGg4PlwVhvU0C5tTYEpUClgdHnUEL6ueCVrAmqj6VLxm+G1UYRcq8QdqFN4aX/DAw==");
        Assert.assertEquals(cfr.phraseTestList.get(1).getPhrase(), "c0bzFojEZwydvAGXWowXLkchjyKqaT/EPDWJAfEVQo9ygpH6//UKPqgkl28INT2ViFqYffq0/cjE0nORHqLzag==");
        Assert.assertEquals(cfr.phraseTestList.get(2).getPhrase(), "xqWn+o2h3sOi06FeWcnCpuKXwNyBeI2R2tH67oFV9to5mgR/BYcIh7Uv9MMFfYVMhjr8punOKRZ0c8/JfwWZNw==");
        Assert.assertEquals(cfr.phraseTestList.get(0).getWordcount(), 4);
        Assert.assertEquals(cfr.phraseTestList.get(1).getWordcount(), 5);
        Assert.assertEquals(cfr.phraseTestList.get(2).getWordcount(), 5);
        Assert.assertEquals(cfr.phraseTestList.get(0).getRarity(), 2.0, 0.0f);
        Assert.assertEquals(cfr.phraseTestList.get(1).getRarity(), 3.0, 0.0f);
        Assert.assertEquals(cfr.phraseTestList.get(2).getRarity(), 5.0, 0.0f);
        Assert.assertEquals(cfr.phraseTestList.get(0).getNum(), 1);
        Assert.assertEquals(cfr.phraseTestList.get(1).getNum(), 1);
        Assert.assertEquals(cfr.phraseTestList.get(2).getNum(), 0);
    }
}
