package com.aus.nlp.model;

public class Word {

    private String text;

    public Word() {}

    public Word(String word) {
        text = word;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
