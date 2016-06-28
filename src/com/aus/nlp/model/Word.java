package com.aus.nlp.model;

public class Word implements Text {

    private String text;

    public Word() {
    }

    public Word(String word) {
        text = word;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
