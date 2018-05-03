package com.aresWikipediaRelationsExtractor.preprocessing.semantics;

import com.aresWikipediaRelationsExtractor.data.DoVerbSequenceIndexes;
import com.aresWikipediaRelationsExtractor.data.HaveBeenSequenceIndexes;
import com.aresWikipediaRelationsExtractor.data.HaveVerbEdSequenceIndexes;

import java.util.List;

public interface SequenceDetector {

    HaveBeenSequenceIndexes detectHaveBeenSequence(List<String> tags);

    HaveVerbEdSequenceIndexes detectHaveVerbEdSequence(List<String> tags);

    DoVerbSequenceIndexes detectDoVerbSequence(List<String> tags);

    boolean isAdverbPredicate(int mainVerbIndex, int i, String tag);

    boolean isNounAdjectivePredicate(int mainVerbIndex, int i, String tag);

    boolean isSubject(int mainVerbIndex, int i, String tag);

    boolean isAfterMainVerbVerbIng(int mainVerbIndex, int i, String tag);

    boolean isBeforeVerbPreposition(int mainVerbIndex, int i, String tag);

    boolean isFirstAfterVerbPreposition(int mainVerbIndex, int afterVerbFirstPrepositionIndex, int i, String tag);

    int detectMainVerbIndex(List<String> tags, String verbTag, int startIndex);
}
