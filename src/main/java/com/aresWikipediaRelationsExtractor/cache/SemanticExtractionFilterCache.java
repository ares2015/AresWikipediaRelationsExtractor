package com.aresWikipediaRelationsExtractor.cache;

import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oliver on 11/2/2016.
 */
public final class SemanticExtractionFilterCache {

    public static final Set<String> semanticExtractionAllowedTags = new HashSet<>();

    public static final Set<String> haveBeenSequenceAtomicAllowedTags = new HashSet<>();

    public static final Set<String> haveBeenSequenceExtendedAllowedTags = new HashSet<>();

    public static final Set<String> haveVerbEdSequenceAtomicAllowedTags = new HashSet<>();

    public static final Set<String> haveVerbEdSequenceExtendedAllowedTags = new HashSet<>();

    public static final Set<String> modalVerbSequenceAtomicAllowedTags = new HashSet<>();

    public static final Set<String> modalVerbSequenceExtendedAllowedTags = new HashSet<>();

    public static final Set<String> doVerbSequenceAtomicAllowedTags = new HashSet<>();

    public static final Set<String> doVerbSequenceExtendedAllowedTags = new HashSet<>();

    public static final Set<String> simpleVerbSequenceExtendedAllowedTags = new HashSet<>();

    public static final Set<String> subjectNounPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> negativeVerbPredicateTags = new HashSet<>();


    public static final Set<String> wordsToFilterCache = new HashSet<>();


    static {

        semanticExtractionAllowedTags.add(Tags.ADJECTIVE);
        semanticExtractionAllowedTags.add(Tags.ADVERB);
        semanticExtractionAllowedTags.add(Tags.AND_OR);
        semanticExtractionAllowedTags.add(Tags.DETERMINER);
        semanticExtractionAllowedTags.add(Tags.DO);
        semanticExtractionAllowedTags.add(Tags.DO_NOT);
        semanticExtractionAllowedTags.add(Tags.CONJUNCTION);
        semanticExtractionAllowedTags.add(Tags.IS_ARE);
        semanticExtractionAllowedTags.add(Tags.IS_ARE_NOT);
        semanticExtractionAllowedTags.add(Tags.HAVE);
        semanticExtractionAllowedTags.add(Tags.HAVE_NOT);
        semanticExtractionAllowedTags.add(Tags.MODAL_VERB);
        semanticExtractionAllowedTags.add(Tags.MODAL_VERB_NOT);
        semanticExtractionAllowedTags.add(Tags.NOUN);
        semanticExtractionAllowedTags.add(Tags.NOT);
        semanticExtractionAllowedTags.add(Tags.NUMBER);
        semanticExtractionAllowedTags.add(Tags.PREPOSITION);
        semanticExtractionAllowedTags.add(Tags.PRONOUN_POSSESIVE);
        semanticExtractionAllowedTags.add(Tags.QUANTIFIER);
        semanticExtractionAllowedTags.add(Tags.TO);
        semanticExtractionAllowedTags.add(Tags.VERB);
        semanticExtractionAllowedTags.add(Tags.VERB_ING);
        semanticExtractionAllowedTags.add(Tags.VERB_ED);
        semanticExtractionAllowedTags.add(Tags.WH_ADVERB);
        semanticExtractionAllowedTags.add(Tags.WH_DETERMINER);
        semanticExtractionAllowedTags.add(Tags.WH_PRONOUN);
        semanticExtractionAllowedTags.add(Tags.WH_PRONOUN_POSSESSIVE);



        simpleVerbSequenceExtendedAllowedTags.add(Tags.HAVE);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.HAVE_NOT);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.VERB);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.IS_ARE);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.IS_ARE_NOT);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.DO);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.DO_NOT);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.NOT);
        simpleVerbSequenceExtendedAllowedTags.add(Tags.ADVERB);

        modalVerbSequenceAtomicAllowedTags.add(Tags.MODAL_VERB);
        modalVerbSequenceAtomicAllowedTags.add(Tags.MODAL_VERB_NOT);
        modalVerbSequenceAtomicAllowedTags.add(Tags.NOT);
        modalVerbSequenceAtomicAllowedTags.add(Tags.DO);
        modalVerbSequenceAtomicAllowedTags.add(Tags.DO_NOT);
        modalVerbSequenceAtomicAllowedTags.add(Tags.IS_ARE);
        modalVerbSequenceAtomicAllowedTags.add(Tags.IS_ARE_NOT);
        modalVerbSequenceAtomicAllowedTags.add(Tags.VERB);
        modalVerbSequenceAtomicAllowedTags.add(Tags.VERB_ED);
        modalVerbSequenceAtomicAllowedTags.add(Tags.VERB_ING);
        modalVerbSequenceAtomicAllowedTags.add(Tags.HAVE);


        modalVerbSequenceExtendedAllowedTags.add(Tags.MODAL_VERB);
        modalVerbSequenceExtendedAllowedTags.add(Tags.MODAL_VERB_NOT);
        modalVerbSequenceExtendedAllowedTags.add(Tags.NOT);
        modalVerbSequenceExtendedAllowedTags.add(Tags.DO);
        modalVerbSequenceExtendedAllowedTags.add(Tags.DO_NOT);
        modalVerbSequenceExtendedAllowedTags.add(Tags.IS_ARE);
        modalVerbSequenceExtendedAllowedTags.add(Tags.IS_ARE_NOT);
        modalVerbSequenceExtendedAllowedTags.add(Tags.VERB);
        modalVerbSequenceExtendedAllowedTags.add(Tags.VERB_ED);
        modalVerbSequenceExtendedAllowedTags.add(Tags.VERB_ING);
        modalVerbSequenceExtendedAllowedTags.add(Tags.ADVERB);
        modalVerbSequenceExtendedAllowedTags.add(Tags.HAVE);

        haveBeenSequenceAtomicAllowedTags.add(Tags.HAVE);
        haveBeenSequenceAtomicAllowedTags.add(Tags.HAVE_NOT);
        haveBeenSequenceAtomicAllowedTags.add(Tags.NOT);
        haveBeenSequenceAtomicAllowedTags.add(Tags.IS_ARE);
        haveBeenSequenceAtomicAllowedTags.add(Tags.IS_ARE_NOT);

        haveBeenSequenceAtomicAllowedTags.add(Tags.HAVE);
        haveBeenSequenceAtomicAllowedTags.add(Tags.HAVE_NOT);
        haveBeenSequenceAtomicAllowedTags.add(Tags.NOT);
        haveBeenSequenceAtomicAllowedTags.add(Tags.IS_ARE);
        haveBeenSequenceAtomicAllowedTags.add(Tags.IS_ARE_NOT);


        haveBeenSequenceExtendedAllowedTags.add(Tags.HAVE);
        haveBeenSequenceExtendedAllowedTags.add(Tags.HAVE_NOT);
        haveBeenSequenceExtendedAllowedTags.add(Tags.NOT);
        haveBeenSequenceExtendedAllowedTags.add(Tags.IS_ARE);
        haveBeenSequenceExtendedAllowedTags.add(Tags.IS_ARE_NOT);
        haveBeenSequenceExtendedAllowedTags.add(Tags.ADVERB);

        haveVerbEdSequenceAtomicAllowedTags.add(Tags.HAVE);
        haveVerbEdSequenceAtomicAllowedTags.add(Tags.HAVE_NOT);
        haveVerbEdSequenceAtomicAllowedTags.add(Tags.NOT);
        haveVerbEdSequenceAtomicAllowedTags.add(Tags.VERB_ED);

        haveVerbEdSequenceExtendedAllowedTags.add(Tags.HAVE);
        haveVerbEdSequenceExtendedAllowedTags.add(Tags.HAVE_NOT);
        haveVerbEdSequenceExtendedAllowedTags.add(Tags.NOT);
        haveVerbEdSequenceExtendedAllowedTags.add(Tags.VERB_ED);
        haveVerbEdSequenceExtendedAllowedTags.add(Tags.ADVERB);


        doVerbSequenceAtomicAllowedTags.add(Tags.DO);
        doVerbSequenceAtomicAllowedTags.add(Tags.DO_NOT);
        doVerbSequenceAtomicAllowedTags.add(Tags.NOT);
        doVerbSequenceAtomicAllowedTags.add(Tags.VERB);

        doVerbSequenceExtendedAllowedTags.add(Tags.DO);
        doVerbSequenceExtendedAllowedTags.add(Tags.DO_NOT);
        doVerbSequenceExtendedAllowedTags.add(Tags.NOT);
        doVerbSequenceExtendedAllowedTags.add(Tags.VERB);
        doVerbSequenceExtendedAllowedTags.add(Tags.ADVERB);


        subjectNounPredicateExtractionAllowedTags.add(Tags.ADJECTIVE);
        subjectNounPredicateExtractionAllowedTags.add(Tags.AND_OR);
        subjectNounPredicateExtractionAllowedTags.add(Tags.CONJUNCTION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NOUN);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NUMBER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.PREPOSITION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.QUANTIFIER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.TO);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ING);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ED);
        subjectNounPredicateExtractionAllowedTags.add(Tags.WH_ADVERB);
        subjectNounPredicateExtractionAllowedTags.add(Tags.WH_DETERMINER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.WH_PRONOUN);
        subjectNounPredicateExtractionAllowedTags.add(Tags.WH_PRONOUN_POSSESSIVE);




        negativeVerbPredicateTags.add(Tags.NOT);
        negativeVerbPredicateTags.add(Tags.DO_NOT);
        negativeVerbPredicateTags.add(Tags.HAVE_NOT);
        negativeVerbPredicateTags.add(Tags.MODAL_VERB_NOT);
        negativeVerbPredicateTags.add(Tags.IS_ARE_NOT);

        wordsToFilterCache.add("the");
        wordsToFilterCache.add("a");
        wordsToFilterCache.add("an");
        wordsToFilterCache.add("The");
        wordsToFilterCache.add("A");
        wordsToFilterCache.add("An");
    }

}
