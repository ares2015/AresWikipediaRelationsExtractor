package com.aresWikipediaRelationsExtractor.extraction.external;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.util.List;

public interface PredicateExtractor {

    void extract(WikipediaProcessingData wikipediaProcessingData, WikipediaProcessingData externalWikipediaProcessingData, String verbPredicate, List<String> verbPredicateTags);

}