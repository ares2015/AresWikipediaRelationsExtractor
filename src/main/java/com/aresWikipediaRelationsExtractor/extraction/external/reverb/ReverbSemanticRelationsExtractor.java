package com.aresWikipediaRelationsExtractor.extraction.external.reverb;



import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.io.IOException;
import java.util.List;

public interface ReverbSemanticRelationsExtractor {

    int extract(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException;

}