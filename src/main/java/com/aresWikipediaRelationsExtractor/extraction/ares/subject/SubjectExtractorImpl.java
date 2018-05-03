package com.aresWikipediaRelationsExtractor.extraction.ares.subject;

import com.aresWikipediaRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 2/16/2017.
 */
public class SubjectExtractorImpl implements SubjectExtractor {

    @Override
    public void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        int extractionEndIndex = getExtractionEndIndex(semanticPreprocessingData);
        if (!semanticPreprocessingData.containsBeforeVerbPreposition()) {
            String atomicSubject = "";
            atomicSubject = extractAtomicSubject(tokensList, tagsList, extractionEndIndex);
            wikipediaProcessingData.setAtomicSubject(atomicSubject);
        }
        if (extractionEndIndex > 1) {
            String extendedSubject = extractExtendedSubject(tokensList, tagsList, extractionEndIndex);
            wikipediaProcessingData.setExtendedSubject(extendedSubject);
        }
    }

    private String extractAtomicSubject(List<String> tokensList, List<String> tagsList, int extractionEndIndex) {
        for (int i = extractionEndIndex; i >= 0; i--) {
            if (i != extractionEndIndex && (Tags.NOUN.equals(tagsList.get(i)) || Tags.VERB_ED.equals(tagsList.get(i)))) {
                return tokensList.get(i);
            }
        }
        throw new IllegalStateException("There is no subject in the sentence");
    }

    private String extractExtendedSubject(List<String> tokensList, List<String> tagsList, int extractionEndIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < extractionEndIndex; i++) {
            if (SemanticExtractionFilterCache.subjectNounPredicateExtractionAllowedTags.contains(tagsList.get(i))) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private int getExtractionEndIndex(SemanticPreprocessingData semanticPreprocessingData) {
        if (semanticPreprocessingData.getModalVerbIndex() > -1) {
            return semanticPreprocessingData.getModalVerbIndex();
        } else if (semanticPreprocessingData.getDoVerbSequenceStartIndex() > -1) {
            return semanticPreprocessingData.getDoVerbSequenceStartIndex();
        } else if (semanticPreprocessingData.getHaveBeenSequenceStartIndex() > -1) {
            return semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        } else if (semanticPreprocessingData.getHaveVerbEdSequenceStartIndex() > -1) {
            return semanticPreprocessingData.getHaveVerbEdSequenceStartIndex();
        } else {
            return semanticPreprocessingData.getVerbIndex();
        }
    }
}
