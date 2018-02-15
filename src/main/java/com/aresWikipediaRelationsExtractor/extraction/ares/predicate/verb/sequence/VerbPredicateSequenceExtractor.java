package com.aresWikipediaRelationsExtractor.extraction.ares.predicate.verb.sequence;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 5/29/2017.
 */
public interface VerbPredicateSequenceExtractor {

    String extract(WikipediaProcessingData wikipediaProcessingData, List<String> tokensList, List<String> tagsList, int sequenceStartIndex, Set<String> allowedTags);

}
