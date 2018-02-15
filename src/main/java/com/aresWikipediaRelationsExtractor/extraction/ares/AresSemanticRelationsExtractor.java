package com.aresWikipediaRelationsExtractor.extraction.ares;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface AresSemanticRelationsExtractor {

    void extract(SemanticPreprocessingData semanticPreprocessingData, WikipediaProcessingData wikipediaProcessingData);

}