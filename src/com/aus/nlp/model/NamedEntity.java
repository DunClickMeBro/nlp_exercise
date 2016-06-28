package com.aus.nlp.model;

public class NamedEntity implements Text {

    private String text;

    public NamedEntity() {}

    public NamedEntity(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
