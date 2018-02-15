package com.aresWikipediaRelationsExtractor.extraction.external.stanford;



import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.io.IOException;
import java.util.List;

public interface StanfordSemanticRelationsExtractor {

    int extract(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException;
}