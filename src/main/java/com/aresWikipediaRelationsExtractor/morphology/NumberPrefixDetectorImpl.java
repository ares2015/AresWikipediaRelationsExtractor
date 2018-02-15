package com.aresWikipediaRelationsExtractor.morphology;

import com.aresWikipediaRelationsExtractor.cache.PrefixesCache;

/**
 * Created by Oliver on 7/18/2017.
 */
public class NumberPrefixDetectorImpl implements NumberPrefixDetector {

    public boolean detect(String token) {
        for (Integer constantPrefixLength : PrefixesCache.prefixesLengthsCacheSet) {
            if (token.length() >= constantPrefixLength && modelContainsPrefix(token, constantPrefixLength)) {
                return true;
            }
        }
        return false;
    }

    private boolean modelContainsPrefix(String token, int endIndex) {
        try {
            Integer length = Integer.valueOf(token.substring(0, endIndex));
            return PrefixesCache.prefixesLengthsCacheSet.contains(length);
        } catch (NumberFormatException e) {
            return false;
        }
    }

}