package com.aresWikipediaRelationsExtractor.data;

/**
 * Created by Oliver on 5/29/2017.
 */
public class DoVerbSequenceIndexes {

    private int startIndex = -1;

    private int endIndex = -1;

    public DoVerbSequenceIndexes() {

    }

    public DoVerbSequenceIndexes(int startIndex, int endIndex) {
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
