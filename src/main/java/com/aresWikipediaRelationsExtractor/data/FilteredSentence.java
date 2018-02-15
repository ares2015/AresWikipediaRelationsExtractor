package com.aresWikipediaRelationsExtractor.data;

import java.util.List;

/**
 * Created by Oliver on 5/25/2017.
 */
public class FilteredSentence {

    private List<String> filteredTags;

    private List<String> filteredTokens;

    public FilteredSentence(List<String> filteredTags, List<String> filteredTokens) {
        this.filteredTags = filteredTags;
        this.filteredTokens = filteredTokens;
    }

    public List<String> getFilteredTokens() {
        return filteredTokens;
    }

    public List<String> getFilteredTags() {
        return filteredTags;
    }
}
