package com.aus.nlp.tokenizer;

import com.aus.nlp.model.NamedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NamedEntityFactory {

    private final Map<String, NamedEntity> entityStore = new ConcurrentHashMap<>();
    private final List<String> rawTokens = new ArrayList<>();

    public void normalizeAndInit(List<String> rawEntityTokens) {
        entityStore.clear();
        rawTokens.clear();
        rawTokens.addAll(rawEntityTokens);

        for (String rawToken : rawEntityTokens) {
            // Easier to deal with than multi-words separated by whitespace
            rawToken = rawToken.replaceAll(" ", "_");
            entityStore.put(rawToken, new NamedEntity(rawToken));
        }
    }

    public NamedEntity getEntity(String token) {
        return entityStore.get(token);
    }

    public List<String> getRawTokens() {
        return rawTokens;
    }
}
