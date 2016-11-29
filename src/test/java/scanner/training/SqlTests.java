package scanner.training;

import com.intellij.vcs.log.Hash;
import org.junit.Test;
import scanner.Word;
import scanner.dbEntry.Database;
import scanner.filtering.Hasher;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by cdeck_000 on 11/28/2016.
 */
public class SqlTests {

    Database db = new Database();

    @Test
    public void testInsertEmail() throws Exception {
        Random rand = new Random();
        Double randNum = rand.nextDouble();
        String emailText = randNum + "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way. When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way. On her way she met a copy. The copy warned the Little Blind Text, that where it came from it would have been rewritten a thousand times and everything that was left from its origin would be the word \"and\" and the Little Blind Text should turn around and return to its own, safe country. But nothing the copy said could convince her and so it didn’t take long until a few insidious Copy Writers ambushed her, made her drunk with Longe and Parole and dragged her into their agency, where they abused her for their projects again and again. And if she hasn’t been rewritten, then they are still using her.Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way. When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline";
        db.insertEmail(emailText);
        assertEquals(db.getEmail(emailText), emailText);
    }

    @Test
    public void testDeleteEmail() throws Exception {
        Random rand = new Random();
        Double randNum = rand.nextDouble();
        String emailText = randNum + "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way. When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way. On her way she met a copy. The copy warned the Little Blind Text, that where it came from it would have been rewritten a thousand times and everything that was left from its origin would be the word \"and\" and the Little Blind Text should turn around and return to its own, safe country. But nothing the copy said could convince her and so it didn’t take long until a few insidious Copy Writers ambushed her, made her drunk with Longe and Parole and dragged her into their agency, where they abused her for their projects again and again. And if she hasn’t been rewritten, then they are still using her.Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way. When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline";
        db.getEmail(emailText)
    }


    @Test
    public void testUpdateConf() throws Exception {

        String word = "Computer";
        word = Hasher.hashSHA(word);
        int conf;
        if(db.getWord(word) == null) {
            db.insertWords(word, .5, 0);
            conf = 50;
        } else {
            Word mWord = db.getWord(word);
            conf = mWord.getConf();
        }

        for(int i=0; i<30; i++){
            db.incrementWordConf(word);
        }

        Word mWord = db.getWord(word);
        int mConf = mWord.getConf();
        assertEquals(mConf, conf+30);
    }

    @Test
    public void testUpdateNorm() throws Exception {
        String word = "Computer";
        word = Hasher.hashSHA(word);
        int norm;
        if(db.getWord(word) == null) {
            db.insertWords(word, .5, 0);
            norm = 50;
        } else {
            Word mWord = db.getWord(word);
            norm = mWord.getNorm();
        }

        for(int i=0; i<30; i++){
            db.incrementWordNorm(word);
        }

        Word mWord = db.getWord(word);
        int mNorm = mWord.getNorm();
        assertEquals(mNorm, norm+30);
    }
}
