package com.aresWikipediaRelationsExtractor.preprocessing;

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

        HaveBeenSequenceIndexes haveBeenSequenceIndexes = findHaveBeenSequenceStartEndIndexes(filteredTags);
        HaveVerbEdSequenceIndexes haveVerbEdSequenceIndexes = findHaveVerbEdSequenceStartEndIndexes(filteredTags);
        DoVerbSequenceIndexes doVerbSequenceIndexes = findDoVerbSequenceStartEndIndexes(filteredTags);

        if (doVerbSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = findMainVerbIndex(filteredTags, Tags.DO, doVerbSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = findMainVerbIndex(filteredTags, Tags.DO_NOT, doVerbSequenceIndexes.getStartIndex());
            }
        } else if (haveBeenSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = findMainVerbIndex(filteredTags, Tags.HAVE, haveBeenSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = findMainVerbIndex(filteredTags, Tags.HAVE_NOT, haveBeenSequenceIndexes.getStartIndex());
            }
        } else if (haveVerbEdSequenceIndexes.getStartIndex() > -1) {
            mainVerbIndex = findMainVerbIndex(filteredTags, Tags.HAVE, haveVerbEdSequenceIndexes.getStartIndex());
            if (mainVerbIndex == -1) {
                mainVerbIndex = findMainVerbIndex(filteredTags, Tags.HAVE_NOT, haveVerbEdSequenceIndexes.getStartIndex());
            }
        } else {
            mainVerbIndex = findMainVerbIndex(filteredTags, Tags.VERB, 0);
            if (mainVerbIndex == -1) {
                mainVerbIndex = findMainVerbIndex(filteredTags, Tags.VERB_ED, 0);
                if (mainVerbIndex == -1) {
                    return semanticPreprocessingData;
                }
            }
        }

        for (int i = 0; i < filteredTags.size(); i++) {
            String tag = filteredTags.get(i);

            if (Tags.MODAL_VERB.equals(tag) || Tags.MODAL_VERB_NOT.equals(tag)) {
                modalVerbIndex = i;
            }
            if (isFirstAfterVerbPreposition(mainVerbIndex, afterVerbFirstPrepositionIndex, i, tag)) {
                afterVerbFirstPrepositionIndex = i;
            }
            if (isBeforeVerbPreposition(mainVerbIndex, i, tag)) {
                containsBeforeVerbPreposition = true;
            }
            if (isAfterMainVerbVerbIng(mainVerbIndex, i, tag)) {
                containsAfterVerbVerbIng = true;
            }
            if (isSubject(mainVerbIndex, i, tag)) {
                containsSubject = true;
            }
            if (isNounAdjectivePredicate(mainVerbIndex, i, tag)) {
                containsNounAdjectivePredicate = true;
            }
            if (isAdverbPredicate(mainVerbIndex, i, tag)) {
                containsAdverbPredicate = true;
            }
        }
        if (canGoToExtraction(mainVerbIndex, containsSubject, containsNounAdjectivePredicate, containsAdverbPredicate)) {
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
            return semanticPreprocessingData;
        } else {
            return semanticPreprocessingData;
        }
    }

    private boolean canGoToExtraction(int mainVerbIndex, boolean containsSubject, boolean containsNounAdjectivePredicate, boolean containsAdverbPredicate) {
        return mainVerbIndex > -1 && containsSubject /*|| (!containsNounAdjectivePredicate && containsAdverbPredicate)*/;
//                ||/*(Tags.IS_ARE.equals(tags.get(mainVerbIndex)) &&*/ !containsNounAdjectivePredicate;

    }

    private boolean isAdverbPredicate(int mainVerbIndex, int i, String tag) {
        return mainVerbIndex > -1 && i > mainVerbIndex && Tags.ADVERB.equals(tag);
    }

    private boolean isNounAdjectivePredicate(int mainVerbIndex, int i, String tag) {
        return mainVerbIndex > -1 && i > mainVerbIndex &&
                ((Tags.NOUN.equals(tag) || Tags.ADJECTIVE.equals(tag)) || Tags.VERB_ED.equals(tag) ||
                        Tags.VERB_ING.equals(tag));
    }

    private boolean isSubject(int mainVerbIndex, int i, String tag) {
        return (Tags.NOUN.equals(tag) || Tags.VERB_ED.equals(tag)) && i < mainVerbIndex;
    }

    private boolean isAfterMainVerbVerbIng(int mainVerbIndex, int i, String tag) {
        return Tags.VERB_ING.equals(tag) && mainVerbIndex < i;
    }

    private boolean isBeforeVerbPreposition(int mainVerbIndex, int i, String tag) {
        return (Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && i < mainVerbIndex;
    }

    private boolean isFirstAfterVerbPreposition(int mainVerbIndex, int afterVerbFirstPrepositionIndex, int i, String tag) {
        return (Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && afterVerbFirstPrepositionIndex == -1 && i > mainVerbIndex;
    }

    private FilteredSentence filterSentence(List<String> tags, List<String> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        List<String> filteredTags = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
//            if (SemanticExtractionFilterCache.semanticExtractionAllowedTags.contains(tag)) {
                if (!Tags.DETERMINER.equals(tag) && !Tags.PRONOUN_POSSESIVE.equals(tag)) {
                    filteredTags.add(tag);
                    filteredTokens.add(tokens.get(i));
                }
//            } else {
//                break;
//            }
        }
        return new FilteredSentence(filteredTags, filteredTokens);
    }

    /*
    have been
     */
    private HaveBeenSequenceIndexes findHaveBeenSequenceStartEndIndexes(List<String> tags) {
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i <= tags.size() - 1; i++) {
            String tag1 = tags.get(i);
            String tag2 = "";
            if (i + 1 < tags.size() - 1) {
                tag2 = tags.get(i + 1);
            }
            String tag3 = "";
            if (i + 2 < tags.size() - 1) {
                tag3 = tags.get(i + 2);
            }
            if (isHaveBeenSequence(tag1, tag2, tag3)) {
                startIndex = i;
                continue;
            }
            if (startIndex > -1 && !Tags.IS_ARE.equals(tag1) && !Tags.VERB_ED.equals(tag1)) {
                endIndex = i;
                break;
            }
        }
        return new HaveBeenSequenceIndexes(startIndex, endIndex);
    }

    private HaveVerbEdSequenceIndexes findHaveVerbEdSequenceStartEndIndexes(List<String> tags) {
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i <= tags.size() - 1; i++) {
            String tag1 = tags.get(i);
            String tag2 = "";
            if (i + 1 < tags.size() - 1) {
                tag2 = tags.get(i + 1);
            }
            String tag3 = "";
            if (i + 2 < tags.size() - 1) {
                tag3 = tags.get(i + 2);
            }
            if (isHaveVerbEdSequence(tag1, tag2, tag3)) {
                startIndex = i;
                continue;
            }
            if (startIndex > -1 && !Tags.VERB_ED.equals(tag1)) {
                endIndex = i;
                break;
            }
        }
        return new HaveVerbEdSequenceIndexes(startIndex, endIndex);
    }

    private boolean isHaveBeenSequence(String tag1, String tag2, String tag3) {
        return ((Tags.HAVE.equals(tag1) || Tags.HAVE_NOT.equals(tag1)) && (Tags.IS_ARE.equals(tag2) /* || Tags.VERB_ED.equals(tag2)*/))
                || (Tags.HAVE.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.IS_ARE.equals(tag3));
    }

    private boolean isHaveVerbEdSequence(String tag1, String tag2, String tag3) {
        return ((Tags.HAVE.equals(tag1) || Tags.HAVE_NOT.equals(tag1)) && (Tags.VERB_ED.equals(tag2)))
                || (Tags.HAVE.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.VERB_ED.equals(tag3));
    }

    private DoVerbSequenceIndexes findDoVerbSequenceStartEndIndexes(List<String> tags) {
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i <= tags.size() - 1; i++) {
            String tag1 = tags.get(i);
            String tag2 = "";
            if (i + 1 < tags.size() - 1) {
                tag2 = tags.get(i + 1);
            }
            String tag3 = "";
            if (i + 2 < tags.size() - 1) {
                tag3 = tags.get(i + 2);
            }
            if (isDoVerbSequence(tag1, tag2, tag3)) {
                startIndex = i;
                continue;
            }
            if (startIndex > -1 && !Tags.VERB.equals(tag1)) {
                endIndex = i;
                break;
            }
        }
        return new DoVerbSequenceIndexes(startIndex, endIndex);
    }

    private boolean isDoVerbSequence(String tag1, String tag2, String tag3) {
        return ((Tags.DO.equals(tag1) || Tags.DO_NOT.equals(tag1)) && (Tags.VERB.equals(tag2)))
                || (Tags.DO.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.VERB.equals(tag3));
    }

    private int findMainVerbIndex(List<String> tags, String verbTag, int startIndex) {
        boolean verbFound = false;
        int verbIndex = -1;
        for (int i = startIndex; i < tags.size(); i++) {
            String tag = tags.get(i);
            if ((verbTag.equals(tag) || Tags.IS_ARE.equals(tag) || Tags.IS_ARE_NOT.equals(tag)) && !verbFound) {
                verbIndex = i;
                return verbIndex;
            }
//            else if ((verbTag.equals(tag) || Tags.IS_ARE.equals(tag) || Tags.IS_ARE_NOT.equals(tag)) && verbFound) {
//                verbIndex = -1;
//            }
        }
        return verbIndex;
    }

}