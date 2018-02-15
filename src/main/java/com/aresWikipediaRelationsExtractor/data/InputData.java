package com.aresWikipediaRelationsExtractor.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oled on 5/16/2017.
 */
public class InputData {

    private List<String> tokensList = new ArrayList<String>();

    private List<String> tagsList = new ArrayList<>();

    private boolean isCapitalizedSequence;

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public List<String> getTokensList() {
        return tokensList;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public boolean isCapitalizedSequence() {
        return isCapitalizedSequence;
    }

    public void setCapitalizedSequence(boolean capitalizedSequence) {
        isCapitalizedSequence = capitalizedSequence;
    }
}