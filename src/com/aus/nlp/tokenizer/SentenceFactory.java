package com.aus.nlp.tokenizer;

import com.aus.nlp.model.Sentence;
import com.aus.nlp.model.Text;

import java.util.ArrayList;
import java.util.List;

public class SentenceFactory {

    private final NamedEntityFactory namedEntityFactory;
    private final WordFactory wordFactory = new WordFactory();

    public SentenceFactory(NamedEntityFactory factory) {
        this.namedEntityFactory = factory;
    }

    public Sentence makeSentence(List<String> textTokens) {
        List<Text> textList = new ArrayList<>();
        for (String token : textTokens) {
            Text nextText = namedEntityFactory.getEntity(token);
            if (nextText != null) {
                textList.add(nextText);
            } else {
                if (wordFactory.filterAccepts(token)) {
                    nextText = wordFactory.getWord(token);
                    textList.add(nextText);
                }
            }
        }
        return new Sentence(textList);
    }
}
