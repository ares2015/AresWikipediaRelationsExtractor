package com.aresWikipediaRelationsExtractor.writer;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.util.List;

public interface WikipediaDataWriter {

    int write(String path, List<WikipediaProcessingData> wikipediaProcessingDataList, int id, boolean append);

}