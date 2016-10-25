package scanner.dbEntry;

import scanner.analysis.TextParser;

/**
 * Created by cdeck_000 on 10/25/2016.
 */
public class TestDriver {
    public static void main(String [ ] args)
    {
        try {
            TextParser tp = new TextParser("");
            String[] word = new String[]{"Spring"};
            String[] phrase = new String[]{};
            DatabaseInput.processInputSHA(word, phrase, .5, .5, 0, 0);
            tp.findWord("Spring");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
