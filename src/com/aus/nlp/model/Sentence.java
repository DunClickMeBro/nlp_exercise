package com.aus.nlp.model;

import java.util.List;

public class Sentence {

    private List<Text> texts;

    public Sentence() {}

    public Sentence(List<Text> texts) {
        this.texts = texts;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }
}
