package com.aresWikipediaRelationsExtractor.extraction.ares.subject;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/16/2017.
 */
public interface SubjectExtractor {

    void extract(WikipediaProcessingData wikipediaProcessingData, SemanticPreprocessingData semanticPreprocessingData);

}
