package scanner;

import scanner.filtering.Hasher;

/**
 * Created by cdeck_000 on 11/28/2016.
 */
public class Email {

    private int id;
    private String emailText;
    private boolean confidential;
    private int loaded;


    public Email(String emailText) {
        this.setEmailText(emailText);
    }

    public Email(String emailText, int id) {
        this.setEmailText(emailText);
        this.setId(id);
    }

    public Email(String emailText, int id, boolean confidential) {
        this.setEmailText(emailText);
        this.setId(id);
        this.setConfidential(confidential);
    }

    public Email(String emailText, int id, boolean confidential, int loaded) {
        this.setEmailText(emailText);
        this.setId(id);
        this.setConfidential(confidential);
        this.setLoaded(loaded);
    }

    public Email() {

    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailTextIn) {
        this.emailText = emailTextIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int idIn) {
        this.id = idIn;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public int isLoaded() {
        return loaded;
    }

    public void setLoaded(int loadedIn) {
        this.loaded = loadedIn;
    }
}
