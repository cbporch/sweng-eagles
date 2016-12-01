package scanner;

import scanner.filtering.Hasher;

/**
 * Created by cdeck_000 on 11/28/2016.
 */
public class Email {

    private int id;
    private String emailText;
    private boolean confidential;


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

    public boolean getConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidentialIn) {
        this.confidential = confidentialIn;
    }

}
