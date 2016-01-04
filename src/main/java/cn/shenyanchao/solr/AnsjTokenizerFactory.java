package cn.shenyanchao.solr;


import cn.shenyanchao.lucene.util.AnsjTokenizer;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        isQuery = getBoolean(args, "isQuery", true);
        pstemming = getBoolean(args, "pstemming", false);
        stopwordsDir = get(args, "words");
    }

    public Tokenizer create(AttributeFactory factory) {
        if (isQuery == true) {
            return new AnsjTokenizer(factory, new ToAnalysis(new Forest[0]), stopwordsDir, pstemming);
        }

        return new AnsjTokenizer(factory, new IndexAnalysis(new Forest[0]), stopwordsDir, pstemming);

    }

}
