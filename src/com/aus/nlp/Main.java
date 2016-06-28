package com.aus.nlp;

import com.aus.nlp.model.Sentence;
import com.aus.nlp.tokenizer.BreakTokenizer;
import com.aus.nlp.tokenizer.NamedEntityFactory;
import com.aus.nlp.tokenizer.SentenceFactory;
import com.aus.nlp.tokenizer.TokenizerFactory;
import com.aus.nlp.util.IOUtil;
import com.aus.nlp.util.PatternUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Path nerDataPath = Paths.get("NER.txt");
        Path nlpDataPath = Paths.get("nlp_data.txt");
        try {
            List<String> entities = Files.readAllLines(nerDataPath)
                    .stream().filter(line -> line.trim().length() > 0)
                    .collect(Collectors.toList());
            Collections.sort(entities, stringLengthCompare.reversed());

            NamedEntityFactory namedEntityFactory = new NamedEntityFactory();
            namedEntityFactory.normalizeAndInit(entities);

            SentenceFactory sentenceFactory = new SentenceFactory(namedEntityFactory);
            List<Sentence> sentenceList = new ArrayList<>();

            BreakTokenizer sentenceTokenizer = TokenizerFactory.makeSentenceTokenizer();
            BreakTokenizer wordTokenizer = TokenizerFactory.makeWordTokenizer();

            StringBuilder sentenceToken;
            for (String line : Files.readAllLines(nlpDataPath)) {
                sentenceTokenizer.setText(line);
                while (sentenceTokenizer.hasMore()) {
                    sentenceToken =
                            new StringBuilder(
                                    PatternUtils.getApostropheWithSMatcher(
                                            sentenceTokenizer.get()).replaceAll(""));
                    for (String entity : entities) {
                        // Not the fastest way to search Strings, but the longer substrings being searched for first
                        // helps
                        int index = sentenceToken.indexOf(entity);
                        if (index != -1) {
                            sentenceToken = sentenceToken.replace(index, index + entity.length(),
                                    entity.replaceAll(" ", "_"));
                        }
                    }

                    wordTokenizer.setText(sentenceToken.toString());
                    sentenceList.add(sentenceFactory.makeSentence(wordTokenizer.tokenize()));

                }
            }

            IOUtil.writeToXML(sentenceList, "sentences.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static  Comparator<String> stringLengthCompare = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2)
        {
            return Integer.compare(o1.length(), o2.length());
        }
    };
}
