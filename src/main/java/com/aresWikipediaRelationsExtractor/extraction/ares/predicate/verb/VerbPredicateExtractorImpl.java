package com.aresWikipediaRelationsExtractor.extraction.ares.predicate.verb;

import com.aresWikipediaRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;
import com.aresWikipediaRelationsExtractor.extraction.ares.predicate.verb.sequence.VerbPredicateSequenceExtractor;

import java.util.List;
import java.util.logging.Logger;

import static com.aresWikipediaRelationsExtractor.cache.SemanticExtractionFilterCache.negativeVerbPredicateTags;

/**
 * Created by Oliver on 2/17/2017.
 */
public class VerbPredicateExtractorImpl implements VerbPredicateExtractor {

    private final static Logger LOGGER = Logger.getLogger(VerbPredicateExtractorImpl.class.getName());

    private VerbPredicateSequenceExtractor verbPredicateSequenceExtractor;

    public VerbPredicateExtractorImpl(VerbPredicateSequenceExtractor verbPredicateSequenceExtractor) {
        this.verbPredicateSequenceExtractor = verbPredicateSequenceExtractor;
    }

    @Override
    public void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();
        String atomicPredicate = "";
        String extendedPredicate = "";
        int verbIndex = semanticPreprocessingData.getVerbIndex();
        int modalVerbIndex = semanticPreprocessingData.getModalVerbIndex();
        int haveBeenSequenceStartIndex = semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        int haveVerbEdSequenceStartIndex = semanticPreprocessingData.getHaveVerbEdSequenceStartIndex();
        int doVerbSequenceStartIndex = semanticPreprocessingData.getDoVerbSequenceStartIndex();
        if (modalVerbIndex > -1) {
            atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceAtomicAllowedTags);
            extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceExtendedAllowedTags);
            wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
            wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
        } else if (haveBeenSequenceStartIndex > -1) {
            atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceAtomicAllowedTags);
            extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceExtendedAllowedTags);
            wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
            wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
        } else if (haveVerbEdSequenceStartIndex > -1) {
            atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceAtomicAllowedTags);
            extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceExtendedAllowedTags);
            wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
            wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
        } else if (doVerbSequenceStartIndex > -1) {
            atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceAtomicAllowedTags);
            extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceExtendedAllowedTags);
            wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
            wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
        } else {
            atomicPredicate = tokensList.get(verbIndex);
            extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                    verbIndex, SemanticExtractionFilterCache.simpleVerbSequenceExtendedAllowedTags);
            wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
            wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
        }
        //LOGGER.info("Extended verb predicate: " + extendedPredicate);
        //LOGGER.info("Atomic verb predicate: " + atomicPredicate);
        //LOGGER.info("Is negative verb predicate: " + isNegativeVerbPredicate);
    }


    private boolean isNegativeVerbPredicate(int searchStartIndex, List<String> tagsList) {
        if (searchStartIndex == -1) {
            return false;
        } else {
            for (int i = searchStartIndex; i < tagsList.size(); i++) {
                String tag = tagsList.get(i);
                if (negativeVerbPredicateTags.contains(tag)) {
                    return true;
                }
            }
            return false;
        }
    }


}