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

    private VerbPredicateSequenceExtractor verbPredicateSequenceExtractor;

    public VerbPredicateExtractorImpl(VerbPredicateSequenceExtractor verbPredicateSequenceExtractor) {
        this.verbPredicateSequenceExtractor = verbPredicateSequenceExtractor;
    }

    @Override
    public void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData) {
        List<String> tokensList = semanticPreprocessingData.getTokensList();
        List<String> tagsList = semanticPreprocessingData.getTagsList();

        int verbIndex = semanticPreprocessingData.getVerbIndex();
        int modalVerbIndex = semanticPreprocessingData.getModalVerbIndex();
        int haveBeenSequenceStartIndex = semanticPreprocessingData.getHaveBeenSequenceStartIndex();
        int haveVerbEdSequenceStartIndex = semanticPreprocessingData.getHaveVerbEdSequenceStartIndex();
        int doVerbSequenceStartIndex = semanticPreprocessingData.getDoVerbSequenceStartIndex();

        if (modalVerbIndex > -1) {
            processModalVerbSequence(wikipediaProcessingData, tokensList, tagsList, modalVerbIndex);
        } else if (haveBeenSequenceStartIndex > -1) {
            processHaveBeenSequence(wikipediaProcessingData, tokensList, tagsList, haveBeenSequenceStartIndex);
        } else if (haveVerbEdSequenceStartIndex > -1) {
            processHaveVerbEdSequence(wikipediaProcessingData, tokensList, tagsList, haveVerbEdSequenceStartIndex);
        } else if (doVerbSequenceStartIndex > -1) {
            processDoVerbSequence(wikipediaProcessingData, tokensList, tagsList, doVerbSequenceStartIndex);
        } else {
            processStandardVerbSequence(wikipediaProcessingData, tokensList, tagsList, verbIndex);
        }
    }

    private void processModalVerbSequence(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList,
                                          List<String> tagsList, int modalVerbIndex) {
        String atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceAtomicAllowedTags);
        String extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                modalVerbIndex, SemanticExtractionFilterCache.modalVerbSequenceExtendedAllowedTags);
        wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
        wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
    }

    private void processHaveBeenSequence(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList,
                                         List<String> tagsList, int haveBeenSequenceStartIndex) {
        String atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceAtomicAllowedTags);
        String extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                haveBeenSequenceStartIndex, SemanticExtractionFilterCache.haveBeenSequenceExtendedAllowedTags);
        wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
        wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
    }

    private void processHaveVerbEdSequence(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList,
                                           List<String> tagsList, int haveVerbEdSequenceStartIndex) {
        String atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceAtomicAllowedTags);
        String extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                haveVerbEdSequenceStartIndex, SemanticExtractionFilterCache.haveVerbEdSequenceExtendedAllowedTags);
        wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
        wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
    }

    private void processDoVerbSequence(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList,
                                       List<String> tagsList, int doVerbSequenceStartIndex) {
        String atomicPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceAtomicAllowedTags);
        String extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                doVerbSequenceStartIndex, SemanticExtractionFilterCache.doVerbSequenceExtendedAllowedTags);
        wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
        wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
    }

    private void processStandardVerbSequence(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList,
                                             List<String> tagsList, int verbIndex) {
        String atomicPredicate = tokensList.get(verbIndex);
        String extendedPredicate = verbPredicateSequenceExtractor.extract(wikipediaProcessingData, tokensList, tagsList,
                verbIndex, SemanticExtractionFilterCache.simpleVerbSequenceExtendedAllowedTags);
        wikipediaProcessingData.setAtomicVerbPredicate(atomicPredicate);
        wikipediaProcessingData.setExtendedVerbPredicate(extendedPredicate);
    }


}