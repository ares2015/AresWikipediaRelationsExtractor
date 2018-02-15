package com.aresWikipediaRelationsExtractor.factories;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.tokens.Tokenizer;

import java.util.List;

public class WikipediaProcessingDataFactoryImpl implements WikipediaProcessingDataFactory {

    private Tokenizer tokenizer;

    public WikipediaProcessingDataFactoryImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public WikipediaProcessingData create(String fileRow) {
        WikipediaProcessingData wikipediaProcessingData = new WikipediaProcessingData();

        String[] split = fileRow.split("#");

        String sentence = split[0];
        List<String> tokens = tokenizer.splitStringIntoList(sentence);
        String filteredSentence = tokenizer.convertTokensListToSentence(tokens);
        wikipediaProcessingData.setSentence(filteredSentence);

        wikipediaProcessingData.setObject(split[1]);
        return wikipediaProcessingData;
    }

}