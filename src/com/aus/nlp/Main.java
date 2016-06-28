package com.aus.nlp;

import com.aus.nlp.concurrent.SentenceExtractorTask;
import com.aus.nlp.model.Sentence;
import com.aus.nlp.model.Text;
import com.aus.nlp.tokenizer.NamedEntityFactory;
import com.aus.nlp.tokenizer.WordFactory;
import com.aus.nlp.util.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {

    private static Comparator<String> stringLengthCompare = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(o1.length(), o2.length());
        }
    };

    public static void main(String[] args) {
        threadPool();
    }

    private static void threadPool() {
        Path nerDataPath = Paths.get("NER.txt");
        try {
            List<String> entities = Files.readAllLines(nerDataPath)
                    .stream().filter(line -> line.trim().length() > 0)
                    .collect(Collectors.toList());
            Collections.sort(entities, stringLengthCompare.reversed());

            NamedEntityFactory namedEntityFactory = new NamedEntityFactory();
            namedEntityFactory.normalizeAndInit(entities);

            WordFactory wordFactory = new WordFactory();
            ConcurrentLinkedQueue<Sentence> sentenceQueue = new ConcurrentLinkedQueue<>();

            extract(namedEntityFactory, wordFactory, sentenceQueue);

            IOUtil.writeToXML(sentenceQueue, "sentences.xml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SentenceExtractorTask makeExtractorTask(NamedEntityFactory nef, WordFactory wf,
                                                           Queue<Sentence> queue, List<String> fileLines) {
        SentenceExtractorTask extractorTask = new SentenceExtractorTask(nef, wf, queue);
        extractorTask.setFileLines(fileLines);
        return extractorTask;
    }

    private static void extract(NamedEntityFactory nef, WordFactory wf, Queue<Sentence> queue) {
        try {

            ZipFile fis = new ZipFile("nlp_data.zip");
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            Collection<Future<?>> futures = new LinkedList<>();

            Future<?> f;
            for (Enumeration e = fis.entries(); e.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) e.nextElement();

                if (!entry.getName().startsWith("__MACOSX") && !entry.isDirectory()) {
                    try {
                        InputStream in = fis.getInputStream(entry);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        f = executorService.submit(makeExtractorTask(nef, wf, queue,
                                br.lines().collect(Collectors.toList())));
                        futures.add(f);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            // Waiting for all the extractor tasks to come back
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
