package scanner;

/**
 * Created by Chris on 10/18/2016.
 */
public class Phrase {
    private String phrase;
    private int wordcount, rarity;
    private boolean num;    // whether numbers effect the probability

    public Phrase(String phrase) {
        this.phrase = phrase;
        wordcount = phrase.split("\\s+").length;
    }

    public Phrase(String phrase, int wordcount) {
        this.phrase = phrase;
        this.wordcount = wordcount;
    }

    public Phrase(String phrase, int wordcount, int rarity, boolean num) {
        this.phrase = phrase;
        this.wordcount = wordcount;
        this.rarity = rarity;
        this.num = num;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getWordcount() {
        return wordcount;
    }

    public int getRarity() {
        return rarity;
    }

    public boolean isNum() {
        return num;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setNum(boolean num) {
        this.num = num;
    }
}
