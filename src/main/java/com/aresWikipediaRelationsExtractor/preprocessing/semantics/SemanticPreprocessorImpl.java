package com.aresWikipediaRelationsExtractor.preprocessing.semantics;

import com.aresWikipediaRelationsExtractor.data.*;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 2/17/2017.
 * <p>
 * Possible scenarios:
 * 1. single verb - Mary sings very nicely (VERB, VERB_ED, HAVE can be main verb)
 * 2. modal verb - Mary can sing very nicely
 * 3. have been sequence - Mary has sung very nicely
 * 4. modal have been sequence - Mary could have sing very nicely
 */
public class SemanticPreprocessorImpl implements SemanticPreprocessor {

    private SequenceDetector sequenceDetector;

    public SemanticPreprocessorImpl(SequenceDetector sequenceDetector) {
        this.sequenceDetector = sequenceDetector;
    }

    @Override
    public SemanticPreprocessingData preprocess(List<String> tokens, List<String> tags) {
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        int mainVerbIndex = -1;
        int modalVerbIndex = -1;
        boolean containsBeforeVerbPreposition = false;
        int afterVerbFirstPrepositionIndex = -1;
        boolean containsAfterVerbVerbIng = false;
        boolean containsSubject = false;
        boolean containsNounAdjectivePredicate = false;
        boolean containsAdverbPredicate = false;

        FilteredSentence filteredSentence = filterSentence(tags, tokens);
        List<String> filteredTags = filteredSentence.getFilteredTags();
        List<String> filteredTokens = filteredSentence.getFilteredTokens();

        HaveBeenSequenceIndexes haveBeenSequenceIndexes = sequenceDetector.detectHaveBeenSequence(filteredTags);
        HaveVerbEdSequenceIndexes haveVerbEdSequenceIndexes = sequenceDetector.detectHaveVerbEdSequence(filteredTags);
        DoVerbSequenceIndexes doVerbSequenceIndexes = sequenceDetector.detectDoVerbSequence(filteredTags);

        mainVerbIndex = findMainVerbIndex(haveBeenSequenceIndexes, haveVerbEdSequenceIndexes, doVerbSequenceIndexes, filteredTags);
        if (mainVerbIndex == -1) {
            return semanticPreprocessingData;
        }

        for (int i = 0; i < filteredTags.size(); i++) {
            String tag = filteredTags.get(i);

            if (Tags.MODAL_VERB.equals(tag) || Tags.MODAL_VERB_NOT.equals(tag)) {
                modalVerbIndex = i;
            }
            if (sequenceDetector.isFirstAfterVerbPreposition(mainVerbIndex, afterVerbFirstPrepositionIndex, i, tag)) {
                afterVerbFirstPrepositionIndex = i;
            }
            if (sequenceDetector.isBeforeVerbPreposition(mainVerbIndex, i, tag)) {
                containsBeforeVerbPreposition = true;
            }
            if (sequenceDetector.isAfterMainVerbVerbIng(mainVerbIndex, i, tag)) {
                containsAfterVerbVerbIng = true;
            }
            if (sequenceDetector.isSubject(mainVerbIndex, i, tag)) {
                containsSubject = true;
            }
            if (sequenceDetector.isNounAdjectivePredicate(mainVerbIndex, i, tag)) {
                containsNounAdjectivePredicate = true;
            }
            if (sequenceDetector.isAdverbPredicate(mainVerbIndex, i, tag)) {
                containsAdverbPredicate = true;
            }
        }

        if (canGoToExtraction(mainVerbIndex, containsSubject, containsNounAdjectivePredicate, containsAdverbPredicate)) {
            populateSemanticAttributes(semanticPreprocessingData, mainVerbIndex, modalVerbIndex, containsBeforeVerbPreposition,
                    afterVerbFirstPrepositionIndex, containsAfterVerbVerbIng, containsSubject, filteredTags, filteredTokens,
                    haveBeenSequenceIndexes, haveVerbEdSequenceIndexes, doVerbSequenceIndexes);
            return semanticPreprocessingData;
        } else {
            return semanticPreprocessingData;
        }
    }

    private void populateSemanticAttributes(SemanticPreprocessingData semanticPreprocessingData, int mainVerbIndex,
                                            int modalVerbIndex, boolean containsBeforeVerbPreposition, int afterVerbFirstPrepositionIndex,
                                            boolean containsAfterVerbVerbIng, boolean containsSubject, List<String> filteredTags, List<String> filteredTokens,
                                            HaveBeenSequenceIndexes haveBeenSequenceIndexes, HaveVerbEdSequenceIndexes haveVerbEdSequenceIndexes, DoVerbSequenceIndexes doVerbSequenceIndexes) {
        semanticPreprocessingData.setTagsList(filteredTags);
        semanticPreprocessingData.setTokensList(filteredTokens);
        semanticPreprocessingData.setContainsSubject(containsSubject);
        semanticPreprocessingData.setContainsBeforeVerbPreposition(containsBeforeVerbPreposition);
        semanticPreprocessingData.setHaveBeenSequenceStartIndex(haveBeenSequenceIndexes.getStartIndex());
        semanticPreprocessingData.setHaveBeenSequenceEndIndex(haveBeenSequenceIndexes.getEndIndex());
        semanticPreprocessingData.setHaveVerbEdSequenceStartIndex(haveVerbEdSequenceIndexes.getStartIndex());
        semanticPreprocessingData.setHaveVerbEdSequenceEndIndex(haveVerbEdSequenceIndexes.getEndIndex());
        semanticPreprocessingData.setDoVerbSequenceStartIndex(doVerbSequenceIndexes.getStartIndex());
        semanticPreprocessingData.setDoVerbSequenceEndIndex(doVerbSequenceIndexes.getEndIndex());
        semanticPreprocessingData.setVerbIndex(mainVerbIndex);
        semanticPreprocessingData.setModalVerbIndex(modalVerbIndex);
        semanticPreprocessingData.setAfterVerbFirstPrepositionIndex(afterVerbFirstPrepositionIndex);
        semanticPreprocessingData.setContainsAfterVerbVerbIng(containsAfterVerbVerbIng);
        semanticPreprocessingData.setCanGoToExtraction(true);
    }

    private FilteredSentence filterSentence(List<String> tags, List<String> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        List<String> filteredTags = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            String token = tokens.get(i);
            String tag = tags.get(i);
            if (!"The".equals(token) && !"the".equals(token) && !"A".equals(token) && !"a".equals(token)
                    && !"An".equals(token) && !"an".equals(token)) {
                filteredTags.add(tag);
                filteredTokens.add(token);
            }
        }
        return new FilteredSentence(filteredTags, filteredTokens);
    }

    private int findMainVerbIndex(HaveBeenSequenceIndexes haveBeenSequenceIndexes, HaveVerbEdSequenceIndexes haveVerbEdSequenceIndexes,
                                  DoVerbSequenceIndexes doVerbSequenceIndexes, List<String> filteredTags) {
        int mainVerbIndex = -1;
        if (doVerbSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.DO, doVerbSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.DO_NOT, doVerbSequenceIndexes.getStartIndex());
            }
        } else if (haveBeenSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.HAVE, haveBeenSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.HAVE_NOT, haveBeenSequenceIndexes.getStartIndex());
            }
        } else if (haveVerbEdSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.HAVE, haveVerbEdSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.HAVE_NOT, haveVerbEdSequenceIndexes.getStartIndex());
            }
        } else {
            mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.VERB, 0);
            if (mainVerbIndex == -1) {
                mainVerbIndex = sequenceDetector.detectMainVerbIndex(filteredTags, Tags.VERB_ED, 0);
            }
        }
        return mainVerbIndex;
    }

    private boolean canGoToExtraction(int mainVerbIndex, boolean containsSubject, boolean containsNounAdjectivePredicate, boolean containsAdverbPredicate) {
        return mainVerbIndex > -1 && containsSubject /*|| (!containsNounAdjectivePredicate && containsAdverbPredicate)*/;
//                ||/*(Tags.IS_ARE.equals(tags.get(mainVerbIndex)) &&*/ !containsNounAdjectivePredicate;
    }
}