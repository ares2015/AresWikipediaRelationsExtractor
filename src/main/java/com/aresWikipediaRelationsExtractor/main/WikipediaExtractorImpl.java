package com.aresWikipediaRelationsExtractor.main;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.extraction.ares.AresExtractionHandler;
import com.aresWikipediaRelationsExtractor.extraction.stanford.StanfordSemanticRelationsExtractor;
import com.aresWikipediaRelationsExtractor.factories.WikipediaProcessingDataFactory;
import com.aresWikipediaRelationsExtractor.utils.FileUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WikipediaExtractorImpl implements WikipediaExtractor {

    private final static Logger LOGGER = Logger.getLogger(WikipediaExtractorImpl.class.getName());

    private WikipediaProcessingDataFactory wikipediaProcessingDataFactory;

    private AresExtractionHandler aresExtractionHandler;

    private StanfordSemanticRelationsExtractor stanfordSemanticRelationsExtractor;

    public WikipediaExtractorImpl(WikipediaProcessingDataFactory wikipediaProcessingDataFactory, AresExtractionHandler aresExtractionHandler,
                                  StanfordSemanticRelationsExtractor stanfordSemanticRelationsExtractor) {
        this.wikipediaProcessingDataFactory = wikipediaProcessingDataFactory;
        this.aresExtractionHandler = aresExtractionHandler;
        this.stanfordSemanticRelationsExtractor = stanfordSemanticRelationsExtractor;
    }

    @Override
    public void extract(String sourceDataDirectoryName) throws IOException {
        long startTime = System.currentTimeMillis();
        int aresId = 1;
        int stanfordId = 1;
        List<WikipediaProcessingData> wikipediaProcessingDataList = new ArrayList<>();
        List<String> fileNames = FileUtils.getFileNames(sourceDataDirectoryName);
        for (String fileName : fileNames) {
            List<String> fileRows = FileUtils.getFileContent(fileName);
            for (String fileRow : fileRows) {
                WikipediaProcessingData wikipediaProcessingData = wikipediaProcessingDataFactory.create(fileRow);
                wikipediaProcessingDataList.add(wikipediaProcessingData);
                if (wikipediaProcessingDataList.size() == 200000) {
                    aresId = aresExtractionHandler.handle(wikipediaProcessingDataList, aresId);
                    stanfordId = stanfordSemanticRelationsExtractor.extract(wikipediaProcessingDataList, stanfordId);
                    wikipediaProcessingDataList.clear();
                }
            }
        }
        aresExtractionHandler.handle(wikipediaProcessingDataList, aresId);
        stanfordSemanticRelationsExtractor.extract(wikipediaProcessingDataList, stanfordId);
    }


}