package com.aus.nlp.util;

import com.aus.nlp.model.Sentence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.List;
import java.util.Queue;

public class IOUtil {

    public static void writeToXML(Sentence sentence, String filename) throws FileNotFoundException {
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));
        encoder.writeObject(sentence);
        encoder.close();
    }

    public static void writeToXML(List<Sentence> sentences, String filename) throws FileNotFoundException {
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));
        encoder.writeObject(sentences);
        encoder.close();
    }

    public static void writeToXML(Queue<Sentence> sentences, String filename) throws FileNotFoundException {
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));
        encoder.writeObject(sentences);
        encoder.close();
    }


    public static Object readFromXML(String filename) throws FileNotFoundException {
        XMLDecoder decoder =
                new XMLDecoder(
                        new BufferedInputStream(
                                new FileInputStream(filename)));

        Object sentences = decoder.readObject();
        decoder.close();
        return sentences;
    }

}
