import scanner.analysis.EmailTextGUI;
import scanner.dbEntry.DatabaseInput;

/**
 * Created by Chris on 10/28/2016.
 */
public class Driver {
    public static void main(String[] args){
        EmailTextGUI e = new EmailTextGUI();
        DatabaseInput d = new DatabaseInput();
        e.main(null);
        d.main(null);
    }


}
