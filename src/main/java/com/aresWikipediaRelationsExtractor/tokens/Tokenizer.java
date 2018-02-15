package com.aresWikipediaRelationsExtractor.tokens;

import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 5/17/2017.
 */
public interface Tokenizer {

    List<String> getTokens(String sentence);

    String removeSpecialCharactersForPreprocessor(String sentence);

    List<String> splitStringIntoList(String sentence);

    String removeCommaAndDot(final String token);

    Set<Integer> getCommaIndexes(List<String> tokens);

    String decapitalize(String token);

    String removeSpecialCharacters(String token);

    String removeBrackets(String token, char bracket1, char bracket2);

    List<String> getSubsentences(String sentence);

    String convertTokensListToSentence(List<String> tokens);

}
