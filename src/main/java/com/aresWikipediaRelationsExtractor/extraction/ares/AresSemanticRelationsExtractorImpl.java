package com.aresWikipediaRelationsExtractor.extraction.ares;


import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;
import com.aresWikipediaRelationsExtractor.extraction.ares.predicate.noun.NounPredicateExtractor;
import com.aresWikipediaRelationsExtractor.extraction.ares.predicate.verb.VerbPredicateExtractor;
import com.aresWikipediaRelationsExtractor.extraction.ares.subject.SubjectExtractor;

/**
 * Created by Oliver on 2/17/2017.
 */
public class AresSemanticRelationsExtractorImpl implements AresSemanticRelationsExtractor {

    private SubjectExtractor subjectExtractor;

    private VerbPredicateExtractor verbPredicateExtractor;

    private NounPredicateExtractor nounPredicateExtractor;


    public AresSemanticRelationsExtractorImpl(SubjectExtractor subjectExtractor, VerbPredicateExtractor verbPredicateExtractor,
                                              NounPredicateExtractor nounPredicateExtractor) {
        this.subjectExtractor = subjectExtractor;
        this.verbPredicateExtractor = verbPredicateExtractor;
        this.nounPredicateExtractor = nounPredicateExtractor;
    }

    @Override
    public void extract(SemanticPreprocessingData semanticPreprocessingData, WikipediaProcessingData wikipediaProcessingData) {
        subjectExtractor.extract(wikipediaProcessingData, semanticPreprocessingData);
        verbPredicateExtractor.extract(wikipediaProcessingData, semanticPreprocessingData);
        nounPredicateExtractor.extract(wikipediaProcessingData, semanticPreprocessingData);
    }

}
