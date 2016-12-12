package scanner.dbEntry;

import scanner.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 10/27/16.
 */
public class CSVFileReader {

    protected  ArrayList<scanner.Word> wordTestList = new ArrayList<>(); //List of all the words for testing.
    protected  ArrayList<scanner.Phrase> phraseTestList = new ArrayList<>(); //List of phrases for testing.

    public CSVFileReader(){
    }

    /**
     * This is the method for processing and inserting CSV files into the database.
     * It takes in a filepath and then reads through that file. It seperates it into two lists, Words and Phrases.
     * Then it takes these lists and adds all of the terms to the database.
     * @param filename = the filepath for the CSV file.
     *  NOTE: Maybe include exceptions for things like if the file has the wrong format.
     *      EX: wrong amout of numbers, or more than one word on a line.
     */
    public void interpretCSVFile(String filename) {
        Database db = new Database();
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

            while ((line = br.readLine()) != null) {    //This loop will go through every line in the given file.

                String[] lineOfItems = line.split(","); //splits up the current line of the file.

                if (lineOfItems.length == 3) //This is a Word.
                {
                    for (int i = 0; i < lineOfItems.length; i++) {
                        if (item.equals("")) {  //First item on a line is the word itself.
                            item = lineOfItems[i];
                        } else if (rarity.equals("")) { //Second item on a line is the rarity of the word.
                            rarity = lineOfItems[i].trim();
                        } else  {
                            numDep = lineOfItems[i].trim();  //This line must be the number dependent info.

                            //Formats the data up so it can be used for Word
                            int intRarity = Integer.parseInt(rarity);

                            //Creates new Word and adds it to listOfWords.
                            newWord = new scanner.Word(item, intRarity, Integer.parseInt(numDep));
                            listOfWords.add(newWord);
                        }}}
                else if (lineOfItems.length == 4) //This is a Phrase
                {
                    for (int i = 0; i < lineOfItems.length; i++) {
                        if (item.equals("")) {      //First item on a line is the phrase itself.
                            item = lineOfItems[i];
                        } else if (count.equals("")) {      //Second item on a line is the count of the phrase.
                            count = lineOfItems[i].trim();
                        } else if (rarity.equals("")) {     //Third item on a line is the rarity of the phrase.
                            rarity = lineOfItems[i].trim();
                        } else {
                            numDep = lineOfItems[i].trim(); //Fourth item must be the number dependency.

                            //Formats the data up so it can be used for Phrase
                            int intRarity = Integer.parseInt(rarity);
                            int intCount = Integer.parseInt(count);

                            //Creates new Phrase and adds it to listOfPhrases.
                            newPhrase = new scanner.Phrase(item,intCount,intRarity,Integer.parseInt(numDep));
                            listOfPhrases.add(newPhrase);
                        }}}
                //For loop that goes through the line and picks out the data and puts it all together into a Word.

                //resets the values for the next line in the file.
                item = "";
                count = "";
                rarity = "";
            }}
        catch (IOException e) {
            e.printStackTrace();
        }

        //Used for testing only
        wordTestList = listOfWords;
        phraseTestList = listOfPhrases;
        //End of testing area

        //These loops add the words and phrases to the database.
        for (int i = 0; i < listOfWords.size(); i++)
        {
            try {
                //System.out.println(listOfWords.get(i).getWord() + " " + listOfWords.get(i).getRarity() + " " + listOfWords.get(i).getNum());
                db.insertWords(listOfWords.get(i).getWord(), listOfWords.get(i).getRarity(), listOfWords.get(i).getNum());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        for (int i = 0; i < listOfPhrases.size(); i++)
        {
            try {
                //System.out.println(listOfPhrases.get(i).getPhrase() + " " + listOfPhrases.get(i).getRarity() + " " + listOfPhrases.get(i).getWordcount() + " " + listOfPhrases.get(i).getNum());
                db.insertPhrases(listOfPhrases.get(i).getPhrase(), listOfPhrases.get(i).getRarity(), listOfPhrases.get(i).getWordcount(), listOfPhrases.get(i).getNum());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}