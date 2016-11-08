package scanner.analysis;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import scanner.dbEntry.Database;
import scanner.filtering.StemAnalyzer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chris on 11/1/2016.
 */
public class DBIndexer {
    StemAnalyzer analyzer;
    Directory index;
    IndexWriter writer;
    IndexSearcher isearcher;
    Version v = Version.LUCENE_6_2_1;
    Database db = new Database();

    public DBIndexer() throws IOException {
        analyzer = new StemAnalyzer();
        index = new RAMDirectory();
        writer = new IndexWriter(index, new IndexWriterConfig(analyzer));
        indexDB();
    }

    private void indexDB() throws IOException {
        ArrayList<String> words = null;
        try {
            words = db.getWords();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String w : words){
            Document d = new Document();
            d.add(new StringField("hash", w, Field.Store.YES));
            writer.addDocument(d);
        }
        writer.close();
    }

    public ArrayList<String> queryIndex(String s) throws IOException {
        DirectoryReader ireader = DirectoryReader.open(index);
        isearcher = new IndexSearcher(ireader);
        TermQuery q = new TermQuery(new Term("hash", s));
        return null;
    }
}

