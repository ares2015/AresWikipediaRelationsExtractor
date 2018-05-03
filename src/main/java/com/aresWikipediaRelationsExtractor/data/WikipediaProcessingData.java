package com.aresWikipediaRelationsExtractor.data;

import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public final class WikipediaProcessingData {

    private String atomicSubject = "";

    private String extendedSubject = "";

    private String atomicVerbPredicate = "";

    private String extendedVerbPredicate = "";

    private String atomicNounPredicate = "";

    private String extendedNounPredicate = "";

    private String sentence;

    private String topic;

    private int verbPredicateEndIndex;

    private List<String> tokensList;

    private List<String> tagsList;

    private boolean isStanfordCorrectRelation;

    private String stanfordSubject;

    private String stanfordRelation;

    private String stanfordObject;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAtomicSubject() {
        return atomicSubject;
    }

    public void setAtomicSubject(String atomicSubject) {
        this.atomicSubject = atomicSubject;
    }

    public String getExtendedSubject() {
        return extendedSubject;
    }

    public void setExtendedSubject(String extendedSubject) {
        this.extendedSubject = extendedSubject;
    }

    public String getAtomicVerbPredicate() {
        return atomicVerbPredicate;
    }

    public void setAtomicVerbPredicate(String atomicVerbPredicate) {
        this.atomicVerbPredicate = atomicVerbPredicate;
    }

    public String getExtendedVerbPredicate() {
        return extendedVerbPredicate;
    }

    public void setExtendedVerbPredicate(String extendedVerbPredicate) {
        this.extendedVerbPredicate = extendedVerbPredicate;
    }

    public String getAtomicNounPredicate() {
        return atomicNounPredicate;
    }

    public void setAtomicNounPredicate(String atomicNounPredicate) {
        this.atomicNounPredicate = atomicNounPredicate;
    }

    public String getExtendedNounPredicate() {
        return extendedNounPredicate;
    }

    public void setExtendedNounPredicate(String extendedNounPredicate) {
        this.extendedNounPredicate = extendedNounPredicate;
    }

    public int getVerbPredicateEndIndex() {
        return verbPredicateEndIndex;
    }

    public void setVerbPredicateEndIndex(int verbPredicateEndIndex) {
        this.verbPredicateEndIndex = verbPredicateEndIndex;
    }

    public List<String> getTokensList() {
        return tokensList;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public boolean isStanfordCorrectRelation() {
        return isStanfordCorrectRelation;
    }

    public void setStanfordCorrectRelation(boolean stanfordCorrectRelation) {
        isStanfordCorrectRelation = stanfordCorrectRelation;
    }

    public String getStanfordSubject() {
        return stanfordSubject;
    }

    public void setStanfordSubject(String stanfordSubject) {
        this.stanfordSubject = stanfordSubject;
    }

    public String getStanfordRelation() {
        return stanfordRelation;
    }

    public void setStanfordRelation(String stanfordRelation) {
        this.stanfordRelation = stanfordRelation;
    }

    public String getStanfordObject() {
        return stanfordObject;
    }

    public void setStanfordObject(String stanfordObject) {
        this.stanfordObject = stanfordObject;
    }
}