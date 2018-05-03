package com.aresWikipediaRelationsExtractor.preprocessing.capitalization;

import com.aresWikipediaRelationsExtractor.cache.ConstantTagsCache;
import com.aresWikipediaRelationsExtractor.data.InputData;
import com.aresWikipediaRelationsExtractor.tags.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public class CapitalizedTokensPreprocessorImpl implements CapitalizedTokensPreprocessor {

    @Override
    public void process(InputData inputData) {
        List<String> tokensList = inputData.getTokensList();
        List<String> tagsList = inputData.getTagsList();
        List<String> processedTokensList = new ArrayList<>();
        List<String> processedTagsList = new ArrayList<>();
        runCapitalizationLogic(tokensList, tagsList, processedTokensList, processedTagsList, inputData);
    }

    private void runCapitalizationLogic(List<String> tokensList, List<String> tagsList, List<String> processedTokensList,
                                        List<String> processedTagsList, InputData inputData) {
        String mergedToken = "";
        outer:
        for (int i = 0; i < tokensList.size(); i++) {
            if (!"".equals(mergedToken)) {
                i = i - 1;
                processedTokensList.add(mergedToken);
                processedTagsList.add(Tags.NOUN);
                mergedToken = "";
            }
            if (!ConstantTagsCache.constantTagsCache.contains(tagsList.get(i)) && Character.isUpperCase(tokensList.get(i).charAt(0))) {
                while (Character.isUpperCase(tokensList.get(i).charAt(0))) {
                    if ("".equals(mergedToken)) {
                        mergedToken = tokensList.get(i);
                    } else {
                        mergedToken += " " + tokensList.get(i);
                    }
                    if (i == tokensList.size() - 1) {
                        processedTokensList.add(mergedToken);
                        processedTagsList.add(Tags.NOUN);
                        break outer;
                    }
                    i++;
                }
            } else {
                processedTokensList.add(tokensList.get(i));
                processedTagsList.add(tagsList.get(i));
            }
        }
        if (processedTokensList.size() > 0) {
            inputData.setCapitalizedSequence(true);
        }
        inputData.setTokensList(processedTokensList);
        inputData.setTagsList(processedTagsList);
    }

}
