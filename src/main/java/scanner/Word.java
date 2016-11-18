package scanner;

import scanner.filtering.Hasher;

/**
 * This class models a row in the Words Database, to better allow passing of
 * relevant data regarding a word throughout the project.
 *
 * Created by chris on 10/20/16.
 * modified on 10/22/16
 *
 */
public class Word {
    private String word;
    private float rarity;
    private int conf, norm;
    private int num;

    public Word(String word, int rarity, int num) {
        this.setWord(word);
        this.rarity = rarity;
        this.num = num;
    }

    public Word(String word, int rarity, int num, int conf, int norm) {
        this.setWord(word);
        this.rarity = rarity;
        this.conf = conf;
        this.norm = norm;
        this.num = num;
    }

    public Word() {

    }

    public String getWord() {
        return word;
    }

    public float getRarity() {
        return rarity;
    }

    public boolean isNum() {
        if(num==1)
            return true;
        else return false;
    }

    public void setWord(String word) {
        word = Hasher.hashSHA(word);
        this.word = word;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNorm() {
        return norm;
    }

    public void setNorm(int norm) {
        this.norm = norm;
    }

    public int getConf() {
        return conf;
    }

    public void setConf(int conf) {
        this.conf = conf;
    }

    public int getNum() {
        return num;
    }

}
