package com.aresWikipediaRelationsExtractor.extraction.external;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredicateExtractorImpl implements PredicateExtractor {

    @Override
    public void extract(WikipediaProcessingData wikipediaProcessingData, WikipediaProcessingData externalWikipediaProcessingData, String verbPredicate, List<String> verbPredicateTags) {
        List<String> verbPredicateTokens = filterTokens(Arrays.asList(verbPredicate.split(" ")));
        extractAtomicVerbPredicate(externalWikipediaProcessingData, verbPredicateTags, verbPredicateTokens);
        extractExtendedVerbPredicate(externalWikipediaProcessingData, verbPredicateTags, verbPredicateTokens);
        extractNounPredicates(wikipediaProcessingData, externalWikipediaProcessingData, verbPredicateTags, verbPredicateTokens);
    }


    private void extractAtomicVerbPredicate(WikipediaProcessingData externalWikipediaProcessingData, List<String> verbPredicateTags, List<String> verbPredicateTokens) {
        for (int i = 0; i < verbPredicateTags.size(); i++) {
            String tag = verbPredicateTags.get(i);
            if ("V".equals(tag) || "IA".equals(tag) || "Ved".equals(tag)) {
                externalWikipediaProcessingData.setAtomicVerbPredicate(verbPredicateTokens.get(i));
            }
        }
    }

    private void extractExtendedVerbPredicate(WikipediaProcessingData externalWikipediaProcessingData, List<String> verbPredicateTags, List<String> verbPredicateTokens) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < verbPredicateTags.size(); i++) {
            String tag = verbPredicateTags.get(i);
            if (Tags.VERB.equals(tag) || Tags.VERB_ED.equals(tag) || Tags.IS_ARE.equals(tag) || Tags.HAVE.equals(tag) || Tags.HAVE_NOT.equals(tag)
                    || Tags.MODAL_VERB.equals(tag) || Tags.MODAL_VERB_NOT.equals(tag)) {
                stringBuilder.append(verbPredicateTokens.get(i));
                stringBuilder.append(" ");
            }
        }
        externalWikipediaProcessingData.setExtendedVerbPredicate(stringBuilder.toString());
    }

    private void extractNounPredicates(WikipediaProcessingData wikipediaProcessingData, WikipediaProcessingData externalWikipediaProcessingData,
                                       List<String> verbPredicateTags, List<String> verbPredicateTokens) {
        List<String> sentenceTokensList = wikipediaProcessingData.getTokensList();
        List<String> sentenceTagsList = wikipediaProcessingData.getTagsList();
        int extendedVerbPredicateEndIndex = getExtendedVerbPredicateEndIndex(verbPredicateTokens, sentenceTokensList);
        int startIndex = 0;
        boolean isFirstNounFound = false;
        if (Tags.PREPOSITION.equals(verbPredicateTags.get(verbPredicateTags.size() - 1))) {
            startIndex = extendedVerbPredicateEndIndex;
        } else {
            startIndex = extendedVerbPredicateEndIndex + 1;
        }
        StringBuilder extendedStringBuilder = new StringBuilder();
        for (int i = startIndex; i < sentenceTokensList.size(); i++) {
            String sentenceToken = sentenceTokensList.get(i);
            extendedStringBuilder.append(sentenceToken);
            extendedStringBuilder.append(" ");
            String sentenceTag = sentenceTagsList.get(i);
            if (!isFirstNounFound && Tags.NOUN.equals(sentenceTag)) {
                wikipediaProcessingData.setAtomicNounPredicate(sentenceToken);
                isFirstNounFound = true;
            }
        }
        externalWikipediaProcessingData.setExtendedNounPredicate(extendedStringBuilder.toString());
    }

    private int getExtendedVerbPredicateEndIndex(List<String> verbPredicateTokens, List<String> sentenceTokens) {
        for (int i = 0; i < sentenceTokens.size(); i++) {
            if (sentenceTokens.get(i).equals(verbPredicateTokens.get(verbPredicateTokens.size() - 1))) {
                return i;
            }
        }
        throw new IllegalStateException("Extended verb predicate does not contain verb !");
    }


    private List<String> filterTokens(List<String> tokensList) {
        List<String> filteredTokens = new ArrayList<>();
        for (String token : tokensList) {
            if (!" ".equals(token) && !"".equals(token)) {
                filteredTokens.add(token);
            }
        }
        return filteredTokens;
    }
}
