package scanner.filter;

/**
 * Created by Chris on 10/18/2016.
 */
public class Phrase {
    private String phrase;
    private int wordcount;

    public Phrase(String phrase) {
        this.phrase = phrase;
        wordcount = phrase.split("\\s+").length;
    }

    public Phrase(String phrase, int wordcount) {
        this.phrase = phrase;
        this.wordcount = wordcount;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getWordcount() {
        return wordcount;
    }
}
