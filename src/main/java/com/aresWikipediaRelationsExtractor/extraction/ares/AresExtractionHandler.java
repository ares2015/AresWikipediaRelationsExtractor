package com.aresWikipediaRelationsExtractor.extraction.ares;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.io.IOException;
import java.util.List;

public interface AresExtractionHandler {

    int handle(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException;

}