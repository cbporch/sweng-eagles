package scanner;

import scanner.filtering.Hasher;

/**
 * This class models a row in the Phrases Database, to better allow passing of
 * relevant data regarding a phrase throughout the project.
 *  Created by Chris on 10/18/2016.
 * Modified on 10/22/16
 *
 */
public class Phrase {
    private String phrase;
    private float rarity;
    private int wordcount, conf, norm;
    private int num;    // whether numbers effect the probability

    public Phrase(String phrase) {
        this.setPhrase(phrase);
        wordcount = phrase.split("\\s+").length;
    }

    public Phrase(String phrase, int wordcount) {
        this.setPhrase(phrase);
        this.wordcount = wordcount;
    }

    public Phrase(String phrase, int wordcount, int rarity, int num) {
        this.setPhrase(phrase);
        this.wordcount = wordcount;
        this.rarity = rarity;
        this.num = num;
    }

    public Phrase(String phrase, int wordcount, int rarity, int conf, int norm, int num) {
        this.setPhrase(phrase);
        this.wordcount = wordcount;
        this.rarity = rarity;
        this.conf = conf;
        this.norm = norm;
        this.num = num;
    }

    public Phrase() {

    }

    public String getPhrase() {
        return phrase;
    }

    public int getWordcount() {
        return wordcount;
    }

    public float getRarity() {
        return rarity;
    }

    public boolean isNum() {
        if(num==1){
            return true;
        }
        else return false;
    }

    public void setPhrase(String phrase) {
        phrase = Hasher.hashSHA(phrase);
        this.phrase = phrase;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }

    public int getConf() {
        return conf;
    }

    public void setConf(int conf) {
        this.conf = conf;
    }

    public int getNorm() {
        return norm;
    }

    public void setNorm(int norm) {
        this.norm = norm;
    }
}
