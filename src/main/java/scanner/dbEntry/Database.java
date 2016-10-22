package scanner.dbEntry;

import scanner.Phrase;
import scanner.Word;
import scanner.filtering.Hasher;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by cdeck_000 on 10/18/2016.
 */
public class Database {

    private static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://asrcemail.cfz28h3zsskv.us-east-1.rds.amazonaws.com/asrcemail";
        String username = "asrc";
        String password = "rOwan!Sw3ng?";
        return DriverManager.getConnection(url, username, password);
    }

    static void insertWords(String wordIn, int rarityIn) throws Exception   {
        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("insert into Words (word, rarity) Values ('%s', %2d);", wordIn, rarityIn);
            System.out.println("\n" + sql);
            statement.executeUpdate(sql);                   //execute the update
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void insertPhrases(String phraseIn, int rarityIn, int count) throws Exception {
        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("insert into Phrases (phrase, rarity, count) Values ('%s', %2d, %2d);", phraseIn, rarityIn, count);
            System.out.println("\n" + sql);
            statement.executeUpdate(sql);                   //execute the update
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<String> getWords() throws Exception {
        ArrayList<String> words = new ArrayList<>();

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select word from Words");
//            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                words.add(rs.getString(1));
            }
//            System.out.println(words);
            System.out.println("select words completed");
            return words;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public static ArrayList<String> getPhrases() throws Exception {
        ArrayList<String> phrases = new ArrayList<>();

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select phrase from Phrases");
//            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                phrases.add(rs.getString(1));
            }
//            System.out.println(phrases);
            System.out.println("select phrases completed");
            return phrases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    /*
     * Method hashes a given word, checks for it in the database and returns it if it is found,
     * otherwise returns null
     *
     * Word is assumed to be stemmed but not hashed before being passed in
     *
     * Output is a Word object containing a hashed string with all other attributes
     */
    public Word getWord(String word){
        Word found = new Word();
        word = Hasher.hashSHA(word);

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select * from Words");
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                if(rs.getString(1).equals(word)){
                    //TODO : get Word attributes from Words Database
                    found.setWord(word);
                    found.setRarity(2);
//                    found.setConf();
//                    found.setNorm();
//                    found.setNum();
                    return found;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /*
     * Method hashes a given phrase, checks for it in the database and returns it if it is found,
     * otherwise returns null
     *
     * Phrase is assumed to be stemmed but not hashed before being passed in.
     *
     * Output is a Phrase object containing a hashed string with all other attributes
     */
    public Phrase getPhrase(String phrase){
        Phrase found = new Phrase();
        phrase = Hasher.hashSHA(phrase);

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select * from Phrases");
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                if(rs.getString(1).equals(phrase)){
                    //TODO : get Phrase attributes from Words Database
                    found.setPhrase(phrase);
                    found.setRarity(2);
                    found.setWordcount(3);
//                    found.setConf();
//                    found.setNorm();
//                    found.setNum();
                    return found;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}