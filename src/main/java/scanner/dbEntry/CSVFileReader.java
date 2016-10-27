package scanner.dbEntry;

import scanner.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 10/27/16.
 */
public class CSVFileReader {

    /**
     * This will take in a file name and it will go through it if its a CSV file and seperate it into an arraylist.
     * You must enter a single words only file
     */
    public static ArrayList<Word> interpretCSVFile(String filename)
    {
        BufferedReader br;
        ArrayList<scanner.Word> listOfWords = new ArrayList<scanner.Word>(); //Returned list of all the words in this file.

        String word = "";   //
        String rarity = ""; //values used to create a new Word
        String numDep = ""; //

        try {
            br = new BufferedReader(new FileReader(filename));
            String line;    //used to read each line from the file.
            scanner.Word newWord; //used to add a word to the listOfWords

            while ((line = br.readLine()) != null) {

                String[] lineOfWords = line.split(","); //splits up the current line of the file.

                //For loop that goes through the line and picks out the data and puts it all together into a Word.
                for (int i = 0; i < lineOfWords.length; i++) {
                    if (word.equals("")) {
                        word = lineOfWords[i];
                    }
                    else if (rarity.equals("")) {
                        rarity = lineOfWords[i].trim();
                    }
                    else if (numDep.equals("")) {
                        numDep = lineOfWords[i].trim();
                        i--;
                    }
                    else {
                        //Cleans the data up so it can be used for Word
                        int actualRarity = Integer.parseInt(rarity);
                        boolean actualNumDep;
                        if (numDep.equals("0")) {
                            actualNumDep = false;
                        }
                        else {
                            actualNumDep = true;
                        }

                        //Creates new Word and adds it to listOfWords.
                        newWord = new scanner.Word(word,actualRarity,actualNumDep);
                        listOfWords.add(newWord);

                        //resets the values.
                        word = "";
                        rarity = "";
                        numDep = "";
                    }}}}
        catch (IOException e) {
            e.printStackTrace();
        }
        return listOfWords;
    }


    /**
     * This will take in a file name and it will go through it if its a CSV file and seperate it into an arraylist.
     * You must enter a phrase only file
     */
    public static ArrayList<scanner.Phrase> interpretCSVPhraseFile(String filename)
    {
        BufferedReader br;
        ArrayList<scanner.Phrase> listOfPhrases = new ArrayList<>(); //Returned list of all the words in this file.

        String phrase = "";   //
        String rarity = ""; //values used to create a new Phrase
        String count = "";  //
        String numDep = ""; //

        try {
            br = new BufferedReader(new FileReader(filename));
            String line;    //used to read each line from the file.
            scanner.Phrase newPhrase; //used to add a phrase to the listOfPhrases

            while ((line = br.readLine()) != null) {

                String[] lineOfPhrases = line.split(","); //splits up the current line of the file.

                //For loop that goes through the line and picks out the data and puts it all together into a Phrase.
                for (int i = 0; i < lineOfPhrases.length; i++) {
                    if (phrase.equals("")) {
                        phrase = lineOfPhrases[i];
                    }
                    else if (count.equals("")) {
                        count = lineOfPhrases[i].trim();
                    }
                    else if (rarity.equals("")) {
                        rarity = lineOfPhrases[i].trim();
                    }
                    else if (numDep.equals("")) {
                        numDep = lineOfPhrases[i].trim();
                        i--;
                    }
                    else {
                        //Cleans the data up so it can be used for Phrase
                        int actualRarity = Integer.parseInt(rarity);
                        int actualCount = Integer.parseInt(count);
                        boolean actualNumDep;
                        if (numDep.equals("0")) {
                            actualNumDep = false;
                        }
                        else {
                            actualNumDep = true;
                        }

                        //Creates new Phrase and adds it to listOfPhrases.
                        newPhrase = new scanner.Phrase(phrase,actualCount,actualRarity,actualNumDep);
                        listOfPhrases.add(newPhrase);

                        //resets the values.
                        phrase = "";
                        count = "";
                        rarity = "";
                        numDep = "";
                    }}}}
        catch (IOException e) {
            e.printStackTrace();
        }
        return listOfPhrases;
    }

}