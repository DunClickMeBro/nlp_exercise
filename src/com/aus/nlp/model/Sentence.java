package com.aus.nlp.model;

import java.util.List;

public class Sentence {

    private List<Word> words;

    public Sentence() {}

    public Sentence(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
