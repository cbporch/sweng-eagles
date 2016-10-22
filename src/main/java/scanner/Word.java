package scanner;

/**
 * Created by chris on 10/20/16.
 */
public class Word {
    private String word;
    private int rarity, conf, norm;
    private boolean num;

    public Word(String word, int rarity, boolean num) {
        this.word = word;
        this.rarity = rarity;
        this.num = num;
    }

    public String getWord() {
        return word;
    }

    public int getRarity() {
        return rarity;
    }

    public boolean isNum() {
        return num;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setNum(boolean num) {
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
}
