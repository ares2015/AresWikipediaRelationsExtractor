package com.aresWikipediaRelationsExtractor.factories;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

public interface WikipediaProcessingDataFactory {

    WikipediaProcessingData create(String fileRow);

}
