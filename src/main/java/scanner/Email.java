package scanner;

import scanner.filtering.Hasher;

/**
 * Created by cdeck_000 on 11/28/2016.
 */
public class Email {

    private int id;
    private String emailText;


    public Email(String emailText) {
        this.setEmailText(emailText);
    }

    public Email(String emailText, int id) {
        this.setEmailText(emailText);
        this.setId(id);
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

}