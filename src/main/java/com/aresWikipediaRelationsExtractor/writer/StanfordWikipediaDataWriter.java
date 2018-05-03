package com.aresWikipediaRelationsExtractor.writer;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import edu.stanford.nlp.ie.util.RelationTriple;

import java.util.List;

public interface StanfordWikipediaDataWriter {

    int write(String path, List<WikipediaProcessingData> wikipediaProcessingDataList, int id, boolean append);
}
