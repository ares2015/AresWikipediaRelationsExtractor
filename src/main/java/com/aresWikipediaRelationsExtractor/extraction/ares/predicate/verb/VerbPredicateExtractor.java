package com.aresWikipediaRelationsExtractor.extraction.ares.predicate.verb;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface VerbPredicateExtractor {

    void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData);

}
