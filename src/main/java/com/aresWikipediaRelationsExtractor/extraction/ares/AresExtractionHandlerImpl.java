package com.aresWikipediaRelationsExtractor.extraction.ares;

import com.aresWikipediaRelationsExtractor.data.InputData;
import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import com.aresWikipediaRelationsExtractor.data.SemanticPreprocessingData;
import com.aresWikipediaRelationsExtractor.factories.InputDataFactory;
import com.aresWikipediaRelationsExtractor.preprocessing.capitalization.CapitalizedTokensPreprocessor;
import com.aresWikipediaRelationsExtractor.preprocessing.semantics.SemanticPreprocessor;
import com.aresWikipediaRelationsExtractor.tagger.PosTagger;
import com.aresWikipediaRelationsExtractor.utils.DateUtils;
import com.aresWikipediaRelationsExtractor.writer.WikipediaDataWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class AresExtractionHandlerImpl implements AresExtractionHandler {

    private final static Logger LOGGER = Logger.getLogger(AresExtractionHandlerImpl.class.getName());

    private String WRITER_PATH = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\AresWikipediaAnalyser\\PostExtraction\\";

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private PosTagger posTagger;

    private SemanticPreprocessor semanticPreprocessor;

    private AresSemanticRelationsExtractor aresSemanticRelationsExtractor;

    private WikipediaDataWriter wikipediaDataWriter;

    public AresExtractionHandlerImpl(InputDataFactory inputDataFactory, CapitalizedTokensPreprocessor capitalizedTokensPreprocessor, PosTagger posTagger,
                                     SemanticPreprocessor semanticPreprocessor, AresSemanticRelationsExtractor aresSemanticRelationsExtractor,
                                     WikipediaDataWriter wikipediaDataWriter) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.posTagger = posTagger;
        this.semanticPreprocessor = semanticPreprocessor;
        this.aresSemanticRelationsExtractor = aresSemanticRelationsExtractor;
        this.wikipediaDataWriter = wikipediaDataWriter;
    }

    @Override
    public int handle(List<WikipediaProcessingData> wikipediaProcessingDataList, int id) throws IOException {
        for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
            String sentence = wikipediaProcessingData.getSentence();
            try {
                List<String> tagSequences = posTagger.tag(sentence);
                InputData inputData = inputDataFactory.create(sentence, tagSequences);
                capitalizedTokensPreprocessor.process(inputData);
                List<String> tagsList = inputData.getTagsList();
                List<String> tokensList = inputData.getTokensList();
                semanticallyExtractSentence(tokensList, tagsList, wikipediaProcessingData);

            } catch (Exception e) {
                continue;
            }
        }
        String actualWriterPath = "";
        String actualDate = DateUtils.getActualDate();
        String actualDateHours = DateUtils.getActualDateHours();

        String actualBackupPath = WRITER_PATH + actualDate;
        Path path = Paths.get(actualBackupPath);
        Files.createDirectories(path);
        actualWriterPath = actualBackupPath + "\\" + "WikipediaAresData-" + actualDateHours+ ".csv";

        return wikipediaDataWriter.write(actualWriterPath, wikipediaProcessingDataList, id, false);
    }

    private void semanticallyExtractSentence(List<String> tokensList, List<String> tagsList, WikipediaProcessingData wikipediaProcessingData) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        wikipediaProcessingData.setTokensList(semanticPreprocessingData.getTokensList());
        wikipediaProcessingData.setTagsList(semanticPreprocessingData.getTagsList());
        if (semanticPreprocessingData.canGoToExtraction()) {
            aresSemanticRelationsExtractor.extract(semanticPreprocessingData, wikipediaProcessingData);
            LOGGER.info("ARES algorithm extraction data: " + wikipediaProcessingData.getAtomicSubject() + " | " + wikipediaProcessingData.getExtendedSubject()
                    + "|" + wikipediaProcessingData.getAtomicVerbPredicate() + "|" + wikipediaProcessingData.getExtendedVerbPredicate() + "|" +
                    wikipediaProcessingData.getAtomicNounPredicate() + "|" + wikipediaProcessingData.getExtendedNounPredicate() + "|" + wikipediaProcessingData.getSentence());
        }
    }


}
