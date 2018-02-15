package com.aresWikipediaRelationsExtractor.data;

/**
 * Created by Oliver on 5/29/2017.
 */
public class HaveVerbEdSequenceIndexes {

    private int startIndex;

    private int endIndex;

    public HaveVerbEdSequenceIndexes() {
    }

    public HaveVerbEdSequenceIndexes(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

}
