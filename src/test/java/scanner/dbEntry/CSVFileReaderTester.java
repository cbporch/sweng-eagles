package scanner.dbEntry;

import scanner.Word;
import scanner.filtering.Hasher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 10/27/16.
 */
public class CSVFileReaderTester {


    public CSVFileReader()
    {
        String fileLocation = "src"+ File.separatorChar
                +"test"+ File.separatorChar
                +"java" + File.separator
                +"scanner"+ File.separatorChar
                +"dbEntry"+ File.separatorChar
                +"test0.csv"; //First entry = dog,4,0
        ArrayList<Word> test = CSVFileReader.interpretCSVFile(fileLocation);
        if (test.size() > 0) {
            assertEquals(Hasher.hashSHA("dog"), test.get(0).getWord());
            assertEquals(4.0, test.get(0).getRarity(),0);
            assertFalse(test.get(0).isNum());
        }
    }

    @Test
    public void interpretCSVPhraseFile() throws Exception {
        CSVFileReader csvfr = new CSVFileReader();
        String fileLocation = "src"+ File.separatorChar
                +"test"+ File.separatorChar
                +"java" + File.separator
                +"scanner"+ File.separatorChar
                +"dbEntry"+ File.separatorChar
                +"test1.csv"; //First entry = the dog is cool,4,2,1
        ArrayList<scanner.Phrase> test = CSVFileReader.interpretCSVPhraseFile(fileLocation);
        if (test.size() > 0) {
            assertEquals(Hasher.hashSHA("the dog is cool"), test.get(0).getPhrase());
            assertEquals(4, test.get(0).getWordcount());
            assertEquals(2.0, test.get(0).getRarity(),0);
            assertTrue(test.get(0).isNum());
    /**
     * This is the method for processing and inserting CSV files into the database.
     * It takes in a filepath and then reads through that file. It seperates it into two lists, Words and Phrases.
     * Then it takes these lists and adds all of the terms to the database.
     * @param filename = the filepath for the CSV file.
     *  NOTE: Maybe include exceptions for things like if the file has the wrong format.
     *      EX: wrong amout of numbers, or more than one word on a line.
     */
    public static void interpretCSVFile(String filename)
    {
        BufferedReader br;
        ArrayList<scanner.Word> listOfWords = new ArrayList<>(); //List of all the words in this file.
        ArrayList<scanner.Phrase> listOfPhrases = new ArrayList<>(); //List of all the phrases in this file.

        String item = "";   //The word, or the phrase being added.
        String count = "";  //Number of words in phrases.
        String rarity = ""; //The level of secure this item is.
        String numDep = ""; //Determines if the word/phrase has secure numbers used.

        try {
            br = new BufferedReader(new FileReader(filename));
            String line;    //Represents one line at a time for the file, while reading.

            scanner.Word newWord; //Represents a word to be added to the database.
            scanner.Phrase newPhrase; //Represents a phrase to be added to the database.

            while ((line = br.readLine()) != null) {

                String[] lineOfItems = line.split(","); //splits up the current line of the file.

                if (lineOfItems.length == 3) //This is a Word.
                {
                    for (int i = 0; i < lineOfItems.length; i++) {
                        if (item.equals("")) {  //First item on a line is the word itself.
                            item = lineOfItems[i];
                        } else if (rarity.equals("")) { //Second item on a line is the rarity of the word.
                            rarity = lineOfItems[i].trim();
                        } else if (numDep.equals("")) {   //Third item on the line is the number dependency.
                            numDep = lineOfItems[i].trim();
                            //Cleans the data up so it can be used for Word
                            int actualRarity = Integer.parseInt(rarity);
                            //Creates new Word and adds it to listOfWords.
                            newWord = new scanner.Word(item, actualRarity, Boolean.parseBoolean(numDep));
                            listOfWords.add(newWord);
                        }}}
                else if (lineOfItems.length == 4) //This is a Phrase
                {
                    for (int i = 0; i < lineOfItems.length; i++) {
                        if (item.equals("")) {      //First item on a line is the word itself.
                            item = lineOfItems[i];
                        } else if (count.equals("")) {
                            count = lineOfItems[i].trim();
                        } else if (rarity.equals("")) {     //Second item on a line is the rarity of the word.
                            rarity = lineOfItems[i].trim();
                        } else if (numDep.equals("")) {
                            numDep = lineOfItems[i].trim();
                            //Cleans the data up so it can be used for Phrase
                            int actualRarity = Integer.parseInt(rarity);
                            int actualCount = Integer.parseInt(count);
                            //Creates new Phrase and adds it to listOfPhrases.
                            newPhrase = new scanner.Phrase(item,actualCount,actualRarity,Boolean.parseBoolean(numDep));
                            listOfPhrases.add(newPhrase);
                        }}}
                //For loop that goes through the line and picks out the data and puts it all together into a Word.

                //resets the values.
                item = "";
                count = "";
                rarity = "";
                numDep = "";
            }}
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}