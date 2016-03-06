package Utils;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerata on 04/03/16.
 */
public class Tokenizer {

    public static List<String> tokenizeSentence(String sentence) {
        List<String> words = new ArrayList<>();

        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>
                (new StringReader(sentence), new CoreLabelTokenFactory(), "untokenizable=noneKeep");
        while(tokenizer.hasNext())
            words.add(tokenizer.next().value());

        return words;
    }
}