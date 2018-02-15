package com.aresWikipediaRelationsExtractor.preprocessing.sentence;

import java.util.List;

public interface SubSentencesProcessor {

    void processSubSentences(List<String> sentences, String sentence);

}
