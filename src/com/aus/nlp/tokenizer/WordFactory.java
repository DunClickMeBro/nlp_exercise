package com.aus.nlp.tokenizer;

import com.aus.nlp.model.Word;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WordFactory {

    private final WordTokenFilter wordTokenFilter = new WordTokenFilter();

    // To avoid having tons of Word objects for equal strings.  Not for concurrent use.
    private final Map<String, Word> wordStore = new HashMap<>();

    public Word getWord(String token) {
        Word word = wordStore.get(token);
        if (word == null) {
            word = new Word(token);
            wordStore.put(token, word);
        }
        return word;
    }

    public boolean filterAccepts(String wordToken) {
        return wordTokenFilter.accepts(wordToken);
    }

    /**
     * This filter gets used to prevent Word objects being created for tokens consisting of whitespace or entirely of
     * non-word (non-alphanumeric) characters.  Word BreakIterator does a pretty good job of considering things like $72 or
     * 3.50 to be "words", but the Word BreakTokenizer produces things like the single spaces between words, commas,
     * and single parentheses  " ", "," ")".  We don't want to let a Word object get made for a single space or comma,
     * but having those tokens available to look at later might help deciding whether or not a series of tokens is really
     * a single named entity.
     *
     * Dropping punctuation does mean that the original text of a Sentence can't be completely reconstructed, though
     * all contained words are present in their original order.
     *
     * Words are also currently kept in their original case, which can lead to redundant Word objects
     */
    private static class WordTokenFilter implements Filter<String> {
        private final Pattern filter = Pattern.compile("[^\\w]|[\\s+]");

        @Override
        public boolean accepts(String s) {
            return !filter.matcher(s).matches();
        }
    }
}
