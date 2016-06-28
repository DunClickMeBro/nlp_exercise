package com.aus.nlp;

import com.aus.nlp.model.Sentence;
import com.aus.nlp.model.Word;
import com.aus.nlp.tokenizer.BreakTokenizer;
import com.aus.nlp.tokenizer.TokenizerFactory;
import com.aus.nlp.tokenizer.WordFactory;
import com.aus.nlp.util.IOUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Path nlpDataPath = Paths.get("nlp_data.txt");
        try {
            // The files are pretty small, so reading it all in is ok.
            List<String> lineList = Files.readAllLines(nlpDataPath);
            BreakTokenizer wordTokenizer = TokenizerFactory.makeWordTokenizer();
            BreakTokenizer sentenceTokenizer = TokenizerFactory.makeSentenceTokenizer();
            WordFactory wordFactory = new WordFactory();
            List<Sentence> sentenceList = new ArrayList<>();

            String sentenceToken;
            for (String line : lineList) {
                sentenceTokenizer.setText(line);
                while (sentenceTokenizer.hasMore()) {
                    sentenceToken = sentenceTokenizer.get();
                    wordTokenizer.setText(sentenceToken);

                    List<Word> sentenceWords = new ArrayList<>();
                    for (String wordToken : wordTokenizer.tokenize()) {
                        if (wordFactory.filterAccepts(wordToken)) {
                            sentenceWords.add(wordFactory.getWord(wordToken));
                        }
                    }
                    sentenceList.add(new Sentence(sentenceWords));
                }
            }

            IOUtil.writeToXML(sentenceList, "sentences.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
