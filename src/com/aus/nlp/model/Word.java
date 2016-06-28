package com.aus.nlp.model;

public class Word implements Text {

    private String text;

    public Word() {}

    public Word(String word) {
        text = word;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
