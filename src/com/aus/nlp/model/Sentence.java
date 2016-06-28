package com.aus.nlp.model;

import java.util.List;

/**
 * My design is oriented toward keeping the order of Text artifacts in a Sentence reconstructable.
 * If the use case were search-heavy, with a very large amount of data, something like an inverted
 * index from NamedEntities to Sentences may have been more appropriate
 */
public class Sentence {

    private List<Text> texts;

    public Sentence() {
    }

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
