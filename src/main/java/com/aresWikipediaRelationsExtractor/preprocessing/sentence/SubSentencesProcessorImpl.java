package com.aresWikipediaRelationsExtractor.preprocessing.sentence;

import com.aresWikipediaRelationsExtractor.tokens.Tokenizer;

import java.util.List;

public class SubSentencesProcessorImpl implements SubSentencesProcessor {

    private Tokenizer tokenizer;

    public SubSentencesProcessorImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void processSubSentences(List<String> sentences, String sentence) {
        List<String> subSentences = tokenizer.getSubsentences(sentence);
        for (String subSentence : subSentences) {
            subSentence = tokenizer.removeSpecialCharactersForPreprocessor(subSentence);
            System.out.println("Preprocessing sentence: " + subSentence);
            sentences.add(subSentence);
        }
    }
}