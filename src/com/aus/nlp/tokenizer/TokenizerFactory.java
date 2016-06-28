package com.aus.nlp.tokenizer;

import java.text.BreakIterator;
import java.util.Locale;

public class TokenizerFactory {

    // Default locale, since all the sample text is in US English
    private static final Locale currentLocale = new Locale("en", "US");

    public static BreakTokenizer makeWordTokenizer() {
        return new BreakTokenizer(BreakIterator.getWordInstance(currentLocale));
    }

    public static BreakTokenizer makeSentenceTokenizer() {
        return new BreakTokenizer(BreakIterator.getSentenceInstance(currentLocale));
    }
}
