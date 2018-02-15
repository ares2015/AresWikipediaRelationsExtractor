package com.aresWikipediaRelationsExtractor.extraction.ares.predicate.noun;


import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 2/16/2017.
 */
public class NounPredicateExtractorImpl implements NounPredicateExtractor {

    private final static Logger LOGGER = Logger.getLogger(NounPredicateExtractorImpl.class.getName());


    @Override
    public void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        int extractionStartIndex = wikipediaProcessingData.getVerbPredicateEndIndex();
        int afterVerbPrepositionIndex = semanticPreprocessingData.getAfterVerbFirstPrepositionIndex();
        if (!semanticPreprocessingData.containsAfterVerbVerbIng()) {
            String atomicNounPredicate = extractAtomicNounPredicate(tokensList, tagsList, extractionStartIndex,
                    afterVerbPrepositionIndex, semanticPreprocessingData.getVerbIndex());
            wikipediaProcessingData.setAtomicNounPredicate(atomicNounPredicate);
            //LOGGER.info("Atomic noun predicate: " + atomicNounPredicate);
        }
        String extendedNounPredicate = extractExtendedNounPredicate(tokensList, extractionStartIndex);

        wikipediaProcessingData.setExtendedNounPredicate(extendedNounPredicate);
        //LOGGER.info("Extended noun predicate: " + extendedNounPredicate);
    }

    private String extractAtomicNounPredicate(List<String> tokensList, List<String> tagsList,
                                              int extractionStartIndex, int afterVerbPrepositionIndex, int mainVerbIndex) {
        int lastNounIndex = getLastNounOrVerbEdIndex(tagsList, extractionStartIndex, afterVerbPrepositionIndex, mainVerbIndex);
        if (lastNounIndex > -1) {
            return tokensList.get(lastNounIndex);
        } else {
            return "";
        }
    }

    private int getLastNounOrVerbEdIndex(List<String> tagsList, int extractionStartIndex, int afterVerbPrepositionIndex,
                                         int mainVerbIndex) {
        int lastNounIndex = -1;
        if (afterVerbPrepositionIndex > -1) {
            for (int i = extractionStartIndex; i <= afterVerbPrepositionIndex; i++) {
                String tag = tagsList.get(i);
                if (mainVerbIndex != i && (Tags.NOUN.equals(tag) || Tags.VERB_ED.equals(tag))) {
                    lastNounIndex = i;
                }
            }
        } else {
            for (int i = extractionStartIndex; i < tagsList.size(); i++) {
                String tag = tagsList.get(i);
                if (Tags.NOUN.equals(tag) || Tags.VERB_ED.equals(tag)) {
                    lastNounIndex = i;
                }
            }
        }
        return lastNounIndex;
    }

    private String extractExtendedNounPredicate(List<String> tokensList, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex; i < tokensList.size(); i++) {
            stringBuilder.append(tokensList.get(i));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
