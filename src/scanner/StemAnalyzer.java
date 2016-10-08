package scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris Porch on 10/7/2016.
 * <p>
 * This class extends the Standard Analyzer from the Lucene analysis library
 * and adds in stemming functionality
 */

/*
 * Copyright 2016 Christopher Porch, Christopher Deck, Steve Leonetti, Tom Miller, Dan Smith, Mike Bayruns
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class StemAnalyzer extends Analyzer {
    private static final CharArraySet ENGLISH_STOP_WORDS_SET;
    private static final int DEFAULT_MAX_TOKEN_LENGTH = 255;
    private int maxTokenLength;
    private static final CharArraySet STOP_WORDS_SET;

    public StemAnalyzer() {
        this(STOP_WORDS_SET);
    }

    public StemAnalyzer(CharArraySet stopWords) {
        this.maxTokenLength = 255;
    }

    protected TokenStreamComponents createComponents(String fieldname) {
        final StandardTokenizer src = new StandardTokenizer();
        src.setMaxTokenLength(this.maxTokenLength);
        StandardFilter tok = new StandardFilter(src);
        LowerCaseFilter tok1 = new LowerCaseFilter(tok);
        StopFilter tok2 = new StopFilter(tok1, STOP_WORDS_SET);
        final EnglishMinimalStemFilter tok3 = new EnglishMinimalStemFilter(tok2); // this is the relevant part
        return new TokenStreamComponents(src, tok3) {
            protected void setReader(Reader reader) {
                src.setMaxTokenLength(maxTokenLength);
                super.setReader(reader);
            }
        };
    }

    static {
        List stopWords = Arrays.asList("a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if",
                "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then",
                "there", "these", "they", "this", "to", "was", "will", "with");
        CharArraySet stopSet = new CharArraySet(stopWords, false);
        ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
        STOP_WORDS_SET = ENGLISH_STOP_WORDS_SET;
    }
}
