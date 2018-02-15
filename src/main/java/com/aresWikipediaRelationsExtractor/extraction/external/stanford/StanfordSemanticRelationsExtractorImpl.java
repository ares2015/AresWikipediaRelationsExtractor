package com.aresWikipediaRelationsExtractor.extraction.external.stanford;


import com.aresWikipediaRelationsExtractor.cache.TagsConversionCache;
import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.extraction.external.PredicateExtractor;
import com.aresWikipediaRelationsExtractor.tags.StanfordTags;
import com.aresWikipediaRelationsExtractor.utils.DateUtils;
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

    private PredicateExtractor predicateExtractor;

    private WikipediaDataWriter wikipediaDataWriter;

    public StanfordSemanticRelationsExtractorImpl(PredicateExtractor predicateExtractor, WikipediaDataWriter wikipediaDataWriter) {
        this.predicateExtractor = predicateExtractor;
        this.wikipediaDataWriter = wikipediaDataWriter;
    }

    @Override
    public int extract(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException {
        List<WikipediaProcessingData> stanfordNewProcessingDataList = new ArrayList<>();
        TreeMap<Integer, WikipediaProcessingData> treeMap = new TreeMap<Integer, WikipediaProcessingData>();

        for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
            String sentence = wikipediaProcessingData.getSentence();
            Document doc = new Document(sentence);
            try {
                for (Sentence sent : doc.sentences()) {
                    for (RelationTriple triple : sent.openieTriples()) {

//                    System.out.println(triple.confidence + "\t" + triple.subject + "\t" + triple.relation + "\t" + triple.object);
                        if (isCorrectVerbPredicate(triple.relation)) {

                            String extendedSubject = "";
                            for (int j = 0; j <= triple.subject.size() - 1; j++) {
                                extendedSubject += " " + triple.subject.get(j).value() + " ";
                            }

                            String extendedVerbPredicate = "";
                            for (int j = 0; j <= triple.relation.size() - 1; j++) {
                                extendedVerbPredicate += " " + triple.relation.get(j).value() + " ";
                            }

                            String[] subjectTokens = extendedSubject.split(" ");
                            String atomicSubject = Arrays.asList(subjectTokens).get(subjectTokens.length - 1);
                            List<String> verbPredicateTags = getVerbPredicateTags(triple.relation);

                            WikipediaProcessingData stanfordWikipediaProcessingData = new WikipediaProcessingData();

                            stanfordWikipediaProcessingData.setSentence(sentence);


                            stanfordWikipediaProcessingData.setAtomicSubject(atomicSubject);
                            stanfordWikipediaProcessingData.setExtendedSubject(extendedSubject);

                            predicateExtractor.extract(wikipediaProcessingData, stanfordWikipediaProcessingData, extendedVerbPredicate, verbPredicateTags);

                            treeMap.put(extendedSubject.length() + stanfordWikipediaProcessingData.getExtendedVerbPredicate().length() +
                                    stanfordWikipediaProcessingData.getExtendedNounPredicate().length(), stanfordWikipediaProcessingData);

                        }
                    }
                }
            } catch (Exception e) {
//                LOGGER.info(e.getCause().toString());
                continue;
            }
            if (treeMap.size() > 0) {
                WikipediaProcessingData selectedNewsData = treeMap.lastEntry().getValue();
                LOGGER.info("STANFORD semantic extraction: atomicSubject = " + selectedNewsData.getAtomicSubject() + ", extendedSubject = " + selectedNewsData.getExtendedSubject() +
                        " atomicVerbPredicate = " + selectedNewsData.getAtomicVerbPredicate() + " extendedVerbPredicate = " + selectedNewsData.getExtendedVerbPredicate() +
                        " atomicNounPredicate = " + selectedNewsData.getAtomicNounPredicate() + " extendedNounPredicate = " + selectedNewsData.getExtendedNounPredicate());
                stanfordNewProcessingDataList.add(selectedNewsData);
                treeMap.clear();
            } else {
                WikipediaProcessingData stanfordWikipediaProcessingData = new WikipediaProcessingData();
                stanfordWikipediaProcessingData.setSentence(sentence);
                stanfordNewProcessingDataList.add(stanfordWikipediaProcessingData);
            }
        }
        String actualWriterPath = "";
        String actualDate = DateUtils.getActualDate();
        String actualDateHours = DateUtils.getActualDateHours();

        String actualBackupPath = WRITER_PATH + actualDate;
        Path path = Paths.get(actualBackupPath);
        Files.createDirectories(path);
        actualWriterPath = actualBackupPath + "\\" + "WikipediaStanfordData-" + actualDateHours+ ".csv";

        return wikipediaDataWriter.write(actualWriterPath, wikipediaProcessingDataList, id, false);
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
//            if(StanfordTags)
        }
        return containsStandardVerb;
    }

    private List<String> getVerbPredicateTags(List<CoreLabel> relation) {
        List<String> tagsList = new ArrayList<>();
        for (CoreLabel coreLabel : relation) {
            tagsList.add(TagsConversionCache.cache.get(coreLabel.tag()));
        }
        return tagsList;
    }
}
