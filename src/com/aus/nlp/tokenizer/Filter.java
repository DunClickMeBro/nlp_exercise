package com.aus.nlp.tokenizer;

interface Filter<T> {
    boolean accepts(T t);
}
