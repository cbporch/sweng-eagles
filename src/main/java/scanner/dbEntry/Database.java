package scanner.dbEntry;

//import org.apache.batik.gvt.filter.BackgroundRable8Bit;
import org.mindrot.jbcrypt.BCrypt;
import scanner.Phrase;
import scanner.Word;
import scanner.filtering.Hasher;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by cdeck_000 on 10/18/2016.
 * Creates a connection to our database, and inserts/gets Words/Phrases
 */
public class Database {

    private final static int CONF = 100;   //conf is the number of confidential emails a word has appeared in
    private final static int NORM = 0;   //norm is the number of normal emails a word has appeared in
    private Connection conn;

    public Database() {
        String url = "jdbc:mysql://asrcemail.cfz28h3zsskv.us-east-1.rds.amazonaws.com/asrcemail";
        String username = "asrc";
        String password = "rOwan!Sw3ng?";
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() throws SQLException {
        conn.close();
    }

    /**
     * Inserts a word into the database. Gets & closes a connection to the database.
     *
     * @param wordIn   - word to insert
     * @param rarityIn - rarity of that word
     * @param numDep   - is the word number dependent
     * @throws Exception When entry fails
     */
    public void insertWords(String wordIn, double rarityIn, int numDep) throws Exception {
        Statement statement = conn.createStatement();   //create statement

        // fill with test values
        double c, n;
        c = 100 * rarityIn;
        n = 100 - c;

        String sql = String.format("insert into Words (word, rarity, NumDep, conf, norm) Values ('%s', '%f', '%d', '%f', '%f');", wordIn, rarityIn, numDep, c, n);
        //System.out.println("\n" + sql);                 //display the update for testing
        statement.executeUpdate(sql);                   //execute the update
    }

    /**
     * Inserts a phrase into the database. Gets & closes a connection to the database.
     *
     * @param phraseIn - phrase to be inserted
     * @param rarityIn - rarity for the phrase
     * @param count    - wordcount of phrase
     * @param numDep   - whether phrase is number dependant
     * @throws Exception
     */
    public void insertPhrases(String phraseIn, double rarityIn, int count, int numDep) throws Exception {
        Statement statement = conn.createStatement();   //create statement

        // fill with test values
        double c, n;
        c = 100 * rarityIn;
        n = 100 - c;

        String sql = String.format("insert into Phrases (phrase, rarity, count, NumDep, conf, norm) Values ('%s', '%f', '%d', '%d', '%f', '%f');", phraseIn, rarityIn, count, numDep, c, n);
        //System.out.println("\n" + sql);                 //testing purposes
        statement.executeUpdate(sql);                   //execute the update
    }

    /**
     * Returns all the hashed words in the database
     *
     * @return an ArrayList of words.
     * @throws Exception
     */
    public ArrayList<String> getWords() throws Exception {
        ArrayList<String> words = new ArrayList<>();
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("select word from Words");   //only selecting the column word
        //System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        while (rs.next()) {
            words.add(rs.getString(1));                 //add the word to the arraylist
        }
        //System.out.println(words);
        System.out.println("select words completed");
        return words;

    }

    /**
     * Returns all the hashed phrases in the database.
     *
     * @return arraylist of phrases
     * @throws Exception
     */
    public ArrayList<String> getPhrases() throws Exception {
        ArrayList<String> phrases = new ArrayList<>();
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("select phrase from Phrases");   //select on phrase column
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        while (rs.next()) {
            phrases.add(rs.getString(1));               //add the phrase to the arraylist
        }
        System.out.println("select phrases completed");
        return phrases;
    }


    /**
     * Hashes the given word and finds it in the database.
     *
     * @param word - word to get from database
     * @return if a word is matched, it is returned
     */
    public Word getWord(String word) throws Exception {
        Word found = new Word();
        //word = Hasher.hashSHA(word);    //hash the word
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("SELECT * from Words WHERE word like '%s'", word);
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        //System.out.println(sql);
        while (rs.next()) {
            if (rs.getString(2).equals(word)) {         //compare the word to the word in the Database
                found.setWord(word);
                found.setRarity(rs.getFloat(3));
                found.setNum(rs.getInt(4));
                found.setConf(rs.getInt(5));
                found.setNorm(rs.getInt(6));
                System.out.println("Good! for words");
                return found;
            }
        }
        return null;
    }

    /**
     * Get a phrase from the database that matches the parameter
     *
     * @param phrase - phrase to look for
     * @return if a phrase is matched, it is returned
     */
    public Phrase getPhrase(String phrase, int N) throws Exception {
        Phrase found = new Phrase();
        //phrase = Hasher.hashSHA(phrase);        //hash the phrase

        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("SELECT * from Phrases WHERE phrase like '%s' AND count like '%d'", phrase, N);
        //System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        while (rs.next()) {
            if (rs.getString(2).equals(phrase)) {
                //TODO : get Phrase attributes from Words Database
                found.setPhrase(phrase);
                found.setRarity(rs.getFloat(3));
                found.setWordcount(rs.getInt(4));
                found.setNum(rs.getInt(5));
                found.setConf(rs.getInt(6));
                found.setNorm(rs.getInt(7));

                System.out.println("Good for phrases!!!");
                return found;
            }
        }
        return null;
    }


    public Boolean checkLogin(String username, String password) throws Exception {
        System.out.println("Username: " + username + " Password: " + password);
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("SELECT * from Logins WHERE Username like '%s'", username);
        System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        while (rs.next()) {
            if (BCrypt.checkpw(password, rs.getString(3))) {
                System.out.println("Login successful.");
                conn.close();                          //close the connection
                return true;
            }
        }
        System.out.println("Login failed.");
        return false;
    }

    public ArrayList<Integer> getWordcounts() {
        try {
            ArrayList<Integer> grams = new ArrayList<>();
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("SELECT DISTINCT count from Phrases");
            //System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                grams.add(rs.getInt(1));
            }
            return grams;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void insertEmail(String emailText) throws Exception{
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("INSERT into UntrainedEmails (EmailText, Author) VALUES ('%s', 'Null')", emailText);
        System.out.println(sql);
        statement.executeUpdate(sql);     //execute the select query
    }

    public String getEmail(String emailText) throws Exception{
        String found;
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("SELECT * from UntrainedEmails WHERE EmailText like '%s'", emailText);
        ResultSet rs = statement.executeQuery(sql);     //execute the select query
        System.out.println(sql);
        while (rs.next()) {
            if (rs.getString(2).equals(emailText)) {         //compare the word to the word in the Database
                found = rs.getString(2);
                return found;
            }
        }
        return null;
    }

    public boolean removeEmailById(int id) throws Exception{
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("DELETE FROM UntrainedEmails WHERE id = '%d'", id);
        statement.executeUpdate(sql);     //execute the select query
        System.out.println(sql);
        return true;
    }

    public boolean removeEmailByText(String emailText) throws Exception{
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("DELETE FROM UntrainedEmails WHERE EmailText = '%d'", emailText);
        statement.executeUpdate(sql);     //execute the select query
        System.out.println(sql);
        return true;
    }


    public void incrementWordConf(String hashedWord) throws Exception{
        Word word = getWord(hashedWord);
        int conf = word.getConf() + 1;
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("UPDATE Words SET Conf = '%d' Where word = '%s'", conf, hashedWord);
        System.out.println(sql);
        statement.executeUpdate(sql);     //execute the select query
    }

    public void incrementWordNorm(String hashedWord) throws Exception{
        Word word = getWord(hashedWord);
        int norm = word.getNorm() + 1;
        Statement statement = conn.createStatement();   //create statement
        String sql = String.format("UPDATE Words SET Norm = '%d' Where word = '%s'", norm, hashedWord);
        System.out.println(sql);
        statement.executeUpdate(sql);     //execute the select query
    }

    public void incrementPhraseConf(String hashedPhrase){

    }
}