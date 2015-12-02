package cn.shenyanchao.solr;

import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AnsjTokenizerFactory
 *
 * @author shenyanchao
 */
public class AnsjTokenizerFactory extends TokenizerFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AnsjTokenizerFactory.class);

    public Set<String> filter = new HashSet<>();

    private boolean pstemming;

    private boolean isQuery;

    private String stopwordsDir;

    public AnsjTokenizerFactory(Map<String, String> args) {
        super(args);
        assureMatchVersion();
        isQuery = getBoolean(args, "isQuery", true);
        pstemming = getBoolean(args, "pstemming", false);
        stopwordsDir = get(args, "words");
        addStopwords(stopwordsDir);
    }

    @Override
    public Tokenizer create(AttributeFactory factory, Reader input) {
        if (isQuery == true) {
            return new AnsjTokenizer(new ToAnalysis(new BufferedReader(input)), input, filter, pstemming);
        } else {
            return new AnsjTokenizer(new IndexAnalysis(new BufferedReader(input)), input, filter, pstemming);
        }
    }


    private void addStopwords(String dir) {
        if (dir == null) {
            return;
        }
        File file = new File(dir);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String word = reader.readLine();
            while (word != null) {
                filter.add(word.trim());
                word = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            LOG.error("No Found stopword file!");
        } catch (IOException e) {
            LOG.error("read stopwordFile error!");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
