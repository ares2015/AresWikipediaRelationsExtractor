package com.aresWikipediaRelationsExtractor.extraction.stanford;


import com.aresWikipediaRelationsExtractor.cache.TagsConversionCache;
import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.tags.StanfordTags;
import com.aresWikipediaRelationsExtractor.utils.DateUtils;
import com.aresWikipediaRelationsExtractor.writer.StanfordWikipediaDataWriter;
import com.aresWikipediaRelationsExtractor.writer.WikipediaDataWriter;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

public class StanfordSemanticRelationsExtractorImpl implements StanfordSemanticRelationsExtractor {

    private final static Logger LOGGER = Logger.getLogger(StanfordSemanticRelationsExtractorImpl.class.getName());

    private String WRITER_PATH = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\AresWikipediaAnalyser\\PostExtraction\\";

    private StanfordWikipediaDataWriter stanfordWikipediaDataWriter;

    public StanfordSemanticRelationsExtractorImpl(StanfordWikipediaDataWriter stanfordWikipediaDataWriter) {
        this.stanfordWikipediaDataWriter = stanfordWikipediaDataWriter;
    }

    @Override
    public int extract(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException {
        for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
            String sentence = wikipediaProcessingData.getSentence();
            Document doc = new Document(sentence);
            try {
                for (Sentence sent : doc.sentences()) {
                    for (RelationTriple triple : sent.openieTriples()) {
                        if (isCorrectVerbPredicate(triple.relation)) {
                            wikipediaProcessingData.setStanfordCorrectRelation(true);
                            wikipediaProcessingData.setStanfordSubject(triple.subject.toString());
                            wikipediaProcessingData.setStanfordRelation(triple.relation.toString());
                            wikipediaProcessingData.setStanfordObject(triple.object.toString());
                        }
                    }
                }
            } catch (Exception e) {
//                LOGGER.info(e.getCause().toString());
                continue;
            }

        }
        String actualWriterPath = "";
        String actualDate = DateUtils.getActualDate();
        String actualDateHours = DateUtils.getActualDateHours();

        String actualBackupPath = WRITER_PATH + actualDate;
        Path path = Paths.get(actualBackupPath);
        Files.createDirectories(path);
        actualWriterPath = actualBackupPath + "\\" + "WikipediaStanfordData-" + actualDateHours + ".csv";

        return stanfordWikipediaDataWriter.write(actualWriterPath, wikipediaProcessingDataList, id, false);
    }

    private boolean isCorrectVerbPredicate(List<CoreLabel> relation) {
        boolean containsStandardVerb = false;
        boolean containsPOSending = false;
        boolean containsPreposition = false;
        boolean containsNumber = false;
        for (CoreLabel coreLabel : relation) {
            if (StanfordTags.VERB_BASE_FORM.equals(coreLabel.tag()) || (StanfordTags.VERB_3RD_PERSON_SINGULAR_PRESENT.equals(coreLabel.tag()))
                    || (StanfordTags.VERB_PAST_PARTICIPLE.equals(coreLabel.tag()) || StanfordTags.VERB_PAST_TENSE.equals(coreLabel.tag()))) {
                containsStandardVerb = true;
            }
            if (StanfordTags.POSSESIVE_ENDING.equals(coreLabel.tag())) {
                containsPOSending = true;
            }
            if (StanfordTags.PREPOSITION_SUB_CONJUNCTION.equals(coreLabel.tag())) {
                containsPreposition = true;
            }
        }
        return containsStandardVerb;
    }


}
