import org.mindrot.jbcrypt.BCrypt;
import scanner.LoginGUI;
import scanner.analysis.EmailTextGUI;
import scanner.dbEntry.DatabaseInput;
import scanner.filtering.Hasher;

/**
 * Created by Chris on 10/28/2016.
 */
public class Driver {
    public static void main(String[] args){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.main(null);
    }


}
