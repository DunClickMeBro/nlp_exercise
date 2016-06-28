package com.aus.nlp.tokenizer;

import com.aus.nlp.model.NamedEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedEntityFactory {

    private final Map<String, NamedEntity> entityStore = new HashMap<>();

    public void normalizeAndInit(List<String> rawEntityTokens) {
        entityStore.clear();
        for (String rawToken : rawEntityTokens) {
            // Easier to deal with than multi-words separated by whitespace
            rawToken = rawToken.replaceAll(" ", "_");
            entityStore.put(rawToken, new NamedEntity(rawToken));
        }
    }

    public NamedEntity getEntity(String token) {
        return entityStore.get(token);
    }
}
