package com.aus.nlp.tokenizer;

import java.util.List;

interface Tokenizer<T> {
    List<T> tokenize();
    T get();
}
