package com.aus.nlp.tokenizer;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * BreakIterator does a decent job identifying simple text boundaries
 */
public class BreakTokenizer implements Tokenizer<String> {

    private final BreakIterator breakIterator;
    private String sourceText;

    BreakTokenizer(BreakIterator iterator) {
        breakIterator = iterator;
    }

    /**
     * Note:  the iterator with be positioned at the end after calling this
     *
     * @return  List of all tokens from the source text, from beginning to end
     */
    @Override
    public List<String> tokenize() {
        breakIterator.first();
        List<String> wordTokens = new ArrayList<>();
        while (hasMore()) {
            wordTokens.add(get());
        }
        return wordTokens;
    }

    /**
     * Take the next token, advance the current iterator position
     * @return next token, null if nothing left
     */
    @Override
    public String get() {
        int currBound = breakIterator.current();
        if (currBound != BreakIterator.DONE) {
            int nextBound = breakIterator.next();
            if (nextBound != BreakIterator.DONE) {
                return sourceText.substring(currBound, nextBound);
            }
        }

        return null;
    }

    public boolean hasMore() {
        if (breakIterator.next() != BreakIterator.DONE){
            breakIterator.previous();
            return true;
        }

        return false;
    }

    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        sourceText = text;
        breakIterator.setText(sourceText);
    }


}
