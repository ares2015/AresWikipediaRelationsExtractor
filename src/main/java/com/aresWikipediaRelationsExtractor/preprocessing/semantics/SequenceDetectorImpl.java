package com.aresWikipediaRelationsExtractor.preprocessing.semantics;

import com.aresWikipediaRelationsExtractor.data.DoVerbSequenceIndexes;
import com.aresWikipediaRelationsExtractor.data.HaveBeenSequenceIndexes;
import com.aresWikipediaRelationsExtractor.data.HaveVerbEdSequenceIndexes;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.List;

public class SequenceDetectorImpl implements SequenceDetector {

    @Override
    public HaveBeenSequenceIndexes detectHaveBeenSequence(List<String> tags) {
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

    @Override
    public HaveVerbEdSequenceIndexes detectHaveVerbEdSequence(List<String> tags) {
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

    @Override
    public DoVerbSequenceIndexes detectDoVerbSequence(List<String> tags) {
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

    @Override
    public boolean isAdverbPredicate(int mainVerbIndex, int i, String tag) {
        return mainVerbIndex > -1 && i > mainVerbIndex && Tags.ADVERB.equals(tag);
    }

    @Override
    public boolean isNounAdjectivePredicate(int mainVerbIndex, int i, String tag) {
        return mainVerbIndex > -1 && i > mainVerbIndex &&
                ((Tags.NOUN.equals(tag) || Tags.ADJECTIVE.equals(tag)) || Tags.VERB_ED.equals(tag) ||
                        Tags.VERB_ING.equals(tag));
    }

    @Override
    public boolean isSubject(int mainVerbIndex, int i, String tag) {
        return (Tags.NOUN.equals(tag) || Tags.VERB_ED.equals(tag)) && i < mainVerbIndex;
    }

    @Override
    public boolean isAfterMainVerbVerbIng(int mainVerbIndex, int i, String tag) {
        return Tags.VERB_ING.equals(tag) && mainVerbIndex < i;
    }

    @Override
    public boolean isBeforeVerbPreposition(int mainVerbIndex, int i, String tag) {
        return (Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && i < mainVerbIndex;
    }

    @Override
    public boolean isFirstAfterVerbPreposition(int mainVerbIndex, int afterVerbFirstPrepositionIndex, int i, String tag) {
        return (Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && afterVerbFirstPrepositionIndex == -1 && i > mainVerbIndex;
    }

    @Override
    public int detectMainVerbIndex(List<String> tags, String verbTag, int startIndex) {
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

    private boolean isHaveBeenSequence(String tag1, String tag2, String tag3) {
        return ((Tags.HAVE.equals(tag1) || Tags.HAVE_NOT.equals(tag1)) && (Tags.IS_ARE.equals(tag2) /* || Tags.VERB_ED.equals(tag2)*/))
                || (Tags.HAVE.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.IS_ARE.equals(tag3));
    }

    private boolean isHaveVerbEdSequence(String tag1, String tag2, String tag3) {
        return ((Tags.HAVE.equals(tag1) || Tags.HAVE_NOT.equals(tag1)) && (Tags.VERB_ED.equals(tag2)))
                || (Tags.HAVE.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.VERB_ED.equals(tag3));
    }

    private boolean isDoVerbSequence(String tag1, String tag2, String tag3) {
        return ((Tags.DO.equals(tag1) || Tags.DO_NOT.equals(tag1)) && (Tags.VERB.equals(tag2)))
                || (Tags.DO.equals(tag1)) && (Tags.NOT.equals(tag2) && Tags.VERB.equals(tag3));
    }
}
