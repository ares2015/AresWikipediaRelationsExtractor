package com.aresWikipediaRelationsExtractor.factories;

import com.aresWikipediaRelationsExtractor.data.InputData;
import com.aresWikipediaRelationsExtractor.tokens.Tokenizer;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class InputDataFactoryImpl implements InputDataFactory {

    private final static Logger LOGGER = Logger.getLogger(InputDataFactoryImpl.class.getName());

    private Tokenizer tokenizer;

    public InputDataFactoryImpl(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public InputData create(String sentence, List<String> tagSequencesList) {
        //LOGGER.info("ENTERING create method of InputDataFactoryImpl... ");
        //LOGGER.info("*********************************************************************");
        InputData inputData = new InputData();

        //LOGGER.info("Processing sentence < " + sentence);
        List<String> tokensList = tokenizer.splitStringIntoList(sentence);

        inputData.setTokensList(tokensList);
        List<String> tagsList = tokenizer.splitStringIntoList(tagSequencesList.get(0));
        inputData.setTagsList(tagsList);


        //LOGGER.info("LEAVING create method of SubPathDataListFactoryImpl... ");
        //LOGGER.info("*********************************************************************");

        return inputData;
    }

}