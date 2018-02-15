package com.aresWikipediaRelationsExtractor.extraction.external.reverb;


import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.extraction.external.PredicateExtractor;
import com.aresWikipediaRelationsExtractor.tagger.PosTagger;
import com.aresWikipediaRelationsExtractor.utils.DateUtils;
import com.aresWikipediaRelationsExtractor.writer.WikipediaDataWriter;
import edu.washington.cs.knowitall.extractor.ReVerbExtractor;
import edu.washington.cs.knowitall.extractor.conf.ConfidenceFunction;
import edu.washington.cs.knowitall.extractor.conf.ReVerbOpenNlpConfFunction;
import edu.washington.cs.knowitall.nlp.ChunkedSentence;
import edu.washington.cs.knowitall.nlp.OpenNlpSentenceChunker;
import edu.washington.cs.knowitall.nlp.extraction.ChunkedBinaryExtraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ReverbSemanticRelationsExtractorImpl implements ReverbSemanticRelationsExtractor {

    private final static Logger LOGGER = Logger.getLogger(ReverbSemanticRelationsExtractorImpl.class.getName());

    private String WRITER_PATH = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\AresWikipediaAnalyser\\PostExtraction\\";

    private PredicateExtractor predicateExtractor;

    private PosTagger posTagger;

    private WikipediaDataWriter wikipediaDataWriter;

    public ReverbSemanticRelationsExtractorImpl(PredicateExtractor predicateExtractor, PosTagger posTagger, WikipediaDataWriter wikipediaDataWriter) {
        this.predicateExtractor = predicateExtractor;
        this.posTagger = posTagger;
        this.wikipediaDataWriter = wikipediaDataWriter;
    }

    @Override
    public int extract(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException {
        List<WikipediaProcessingData> reverbNewProcessingDataList = new ArrayList<>();

        ReVerbExtractor reverb = new ReVerbExtractor();
        OpenNlpSentenceChunker chunker = new OpenNlpSentenceChunker();
        ConfidenceFunction confFunc = new ReVerbOpenNlpConfFunction();

        int nrSemanticallyProcessedSentences = 0;

        for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
            String sentence = wikipediaProcessingData.getSentence();

            WikipediaProcessingData reverbWikipediaProcessingData = new WikipediaProcessingData();
            reverbWikipediaProcessingData.setSentence(sentence);
            reverbNewProcessingDataList.add(reverbWikipediaProcessingData);
            ChunkedSentence sent = chunker.chunkSentence(sentence);
            try {
                for (ChunkedBinaryExtraction extr : reverb.extract(sent)) {
                    double conf = confFunc.getConf(extr);

                    String extendedSubject = extr.getArgument1().getText();
                    String extendedVerbPredicate = extr.getRelation().getText();
                    String[] subjectTokens = extendedSubject.split(" ");
                    String atomicSubject = Arrays.asList(subjectTokens).get(subjectTokens.length - 1);
                    List<String> extendedVerbPredicateTags = Arrays.asList(posTagger.tag(extendedVerbPredicate).get(0).split(" "));

                    reverbWikipediaProcessingData.setAtomicSubject(atomicSubject);
                    reverbWikipediaProcessingData.setExtendedSubject(extendedSubject);

                    predicateExtractor.extract(wikipediaProcessingData, reverbWikipediaProcessingData, extendedVerbPredicate, extendedVerbPredicateTags);

                    LOGGER.info("REVERB semantic extraction: atomicSubject = " + atomicSubject + ", extendedSubject = " + extendedSubject +
                            " atomicVerbPredicate = " + reverbWikipediaProcessingData.getAtomicVerbPredicate() + " extendedVerbPredicate = " + reverbWikipediaProcessingData.getExtendedVerbPredicate() +
                            " atomicNounPredicate = " + reverbWikipediaProcessingData.getAtomicNounPredicate() + " extendedNounPredicate = " + reverbWikipediaProcessingData.getExtendedNounPredicate() + " Conf=" + conf);
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
                continue;
            }
        }
        LOGGER.info("REVERB semantically processed " + nrSemanticallyProcessedSentences + " sentences");

        String actualWriterPath = "";
        String actualDate = DateUtils.getActualDate();
        String actualDateHours = DateUtils.getActualDateHours();

        String actualBackupPath = WRITER_PATH + actualDate;
        Path path = Paths.get(actualBackupPath);
        Files.createDirectories(path);
        actualWriterPath = actualBackupPath + "\\" + "WikipediaReverbData-" + actualDateHours+ ".csv";

        return wikipediaDataWriter.write(actualWriterPath, wikipediaProcessingDataList, id, false);
    }

}