package com.aus.nlp.concurrent;


import com.aus.nlp.model.Sentence;
import com.aus.nlp.tokenizer.*;
import com.aus.nlp.util.PatternUtils;

import java.util.List;
import java.util.Queue;

public class SentenceExtractorTask implements Runnable {

    private final BreakTokenizer sentenceTokenizer = TokenizerFactory.makeSentenceTokenizer();
    private final BreakTokenizer wordTokenizer = TokenizerFactory.makeWordTokenizer();

    private final NamedEntityFactory namedEntityFactory;
    private final SentenceFactory sentenceFactory;
    private final Queue<Sentence> sentenceQueue;
    private List<String> fileLines;

    public SentenceExtractorTask(NamedEntityFactory namedEntityFactory, WordFactory wordFactory,
                                 Queue<Sentence> sentenceQueue) {
        this.namedEntityFactory = namedEntityFactory;
        this.sentenceFactory = new SentenceFactory(namedEntityFactory, wordFactory);
        this.sentenceQueue = sentenceQueue;
    }

    public void setFileLines(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    @Override
    public void run() {
        StringBuilder sentenceToken;
        for (String line : fileLines) {
            sentenceTokenizer.setText(line);
            while (sentenceTokenizer.hasMore()) {
                sentenceToken =
                        new StringBuilder(
                                PatternUtils.getApostropheWithSMatcher(
                                        sentenceTokenizer.get()).replaceAll(""));
                for (String entity : namedEntityFactory.getRawTokens()) {
                    // Not the fastest way to search Strings, but the longer substrings being searched for first
                    // helps
                    int index = sentenceToken.indexOf(entity);
                    if (index != -1) {
                        sentenceToken = sentenceToken.replace(index, index + entity.length(),
                                entity.replaceAll(" ", "_"));
                    }
                }

                wordTokenizer.setText(sentenceToken.toString());
                sentenceQueue.add(sentenceFactory.makeSentence(wordTokenizer.tokenize()));

            }
        }
    }
}
