package cn.shenyanchao.solr;

import java.util.Map;

import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.nlpcn.commons.lang.tire.domain.Forest;

import cn.shenyanchao.lucene.util.AnsjTokenizer;

/**
 * AnsjTokenizerFactory
 *
 * @author shenyanchao
 */
public class AnsjTokenizerFactory extends TokenizerFactory {

    private boolean pstemming;

    private boolean isQuery;

    private String stopwordsDir;

    public AnsjTokenizerFactory(Map<String, String> args) {
        super(args);
        isQuery = getBoolean(args, "isQuery", true);
        pstemming = getBoolean(args, "pstemming", false);
        stopwordsDir = get(args, "words");
    }

    @Override
    public Tokenizer create(AttributeFactory factory) {
        if (isQuery == true) {
            return new AnsjTokenizer(factory, new ToAnalysis(new Forest[0]), stopwordsDir, pstemming);
        }
        return new AnsjTokenizer(factory, new IndexAnalysis(new Forest[0]), stopwordsDir, pstemming);

    }

}
