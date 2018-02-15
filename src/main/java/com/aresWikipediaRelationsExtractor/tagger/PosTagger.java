package com.aresWikipediaRelationsExtractor.tagger;

import com.aresWikipediaRelationsExtractor.cache.ConstantWordsCache;
import com.aresWikipediaRelationsExtractor.cache.TagsConversionCache;
import com.aresWikipediaRelationsExtractor.morphology.NumberPrefixDetector;
import com.aresWikipediaRelationsExtractor.morphology.NumberPrefixDetectorImpl;
import com.aresWikipediaRelationsExtractor.tags.StanfordTags;
import com.aresWikipediaRelationsExtractor.tags.Tags;
import com.aresWikipediaRelationsExtractor.tokens.Tokenizer;
import com.aresWikipediaRelationsExtractor.tokens.TokenizerImpl;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PosTagger {

    private Properties props;

    private StanfordCoreNLP pipeline;

    private Tokenizer tokenizer = new TokenizerImpl();

    private NumberPrefixDetector numberPrefixDetector = new NumberPrefixDetectorImpl();

    public PosTagger() {
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        pipeline = new StanfordCoreNLP(props);
    }

    public List<String> tag(String inputSentence) {
        List<String> tagSequences = new ArrayList<>();
        List<String> tokens = tokenizer.splitStringIntoList(inputSentence);
        Set<Integer> commaIndexes = tokenizer.getCommaIndexes(tokens);
        int sentenceLength = tokens.size();
        StringBuilder stringBuilder = new StringBuilder();
        Annotation annotation = new Annotation(inputSentence);
        pipeline.annotate(annotation);
        if (annotation.get(CoreAnnotations.SentencesAnnotation.class).size() > 0) {
            CoreMap processedSentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
            int index = 0;
            for (CoreLabel stfToken : processedSentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = stfToken.get(CoreAnnotations.TextAnnotation.class);
                String tag = stfToken.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (isStandardWord(word, tag)) {
                    if (numberPrefixDetector.detect(word)) {
                        stringBuilder.append(Tags.NUMBER);
                    } else {
                        if (isCapitalizedNoun(index, word, tag)) {
                            stringBuilder.append(Tags.NOUN);
                        } else {
                            String token = tokens.get(index);
                            if (token.contains(",") || token.contains(".")) {
                                token = tokenizer.removeCommaAndDot(token);
                            }
                            if (isConstantWord(token)) {
                                stringBuilder.append(ConstantWordsCache.constantWordsCacheMap.get(tokenizer.decapitalize(token)));
                            } else {
                                stringBuilder.append(TagsConversionCache.cache.get(tag));
                            }
                        }
                    }
                    if (commaIndexes.contains(index)) {
                        tagSequences.add(stringBuilder.toString());
                        stringBuilder.setLength(0);
                    }
                    if (index <= sentenceLength - 1) {
                        stringBuilder.append(" ");
                    }
                    index++;
                }
            }
            tagSequences.add(stringBuilder.toString());
        }
        return tagSequences;
    }

    private boolean isConstantWord(String token) {
        return ConstantWordsCache.constantWordsCacheMap.containsKey(tokenizer.decapitalize(token));
    }

    private boolean isCapitalizedNoun(int index, String word, String tag) {
        return index > 0 && !Tags.PRONOUN_PERSONAL.equals(tag) && Character.isUpperCase(word.charAt(0));
    }

    private boolean isStandardWord(String word, String tag) {
        return !",".equals(tag) && !".".equals(tag) && !StanfordTags.POSSESIVE_ENDING.equals(tag) && !"n't".equals(word);
    }

}