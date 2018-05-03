package com.aresWikipediaRelationsExtractor.preprocessing;

import com.aresWikipediaRelationsExtractor.data.InputData;
import com.aresWikipediaRelationsExtractor.factories.InputDataFactory;
import com.aresWikipediaRelationsExtractor.preprocessing.capitalization.CapitalizedTokensPreprocessor;
import com.aresWikipediaRelationsExtractor.preprocessing.capitalization.CapitalizedTokensPreprocessorImpl;
import com.aresWikipediaRelationsExtractor.tagger.PosTagger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CapitalizedTokensProcessorTest {

    private CapitalizedTokensPreprocessor capitalizedTokensProcessor = new CapitalizedTokensPreprocessorImpl();

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");

    InputDataFactory inputDataFactory = (InputDataFactory) context.getBean("inputDataFactory");

    PosTagger posTagger = (PosTagger) context.getBean("posTagger");


    @Test
    public void test() {
        List<String> tokensList = new ArrayList<>();
        tokensList.add("New");
        tokensList.add("York");
        tokensList.add("City");
        tokensList.add("Police");
        tokensList.add("Department");
        tokensList.add("is");
        tokensList.add("the");
        tokensList.add("best");
        tokensList.add("one");
        tokensList.add("in");
        tokensList.add("the");
        tokensList.add("United");
        tokensList.add("States");
        tokensList.add("of");
        tokensList.add("America");

        List<String> tagsList = new ArrayList<>();
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("IA");
        tagsList.add("DET");
        tagsList.add("AJ");
        tagsList.add("NR");
        tagsList.add("PR");
        tagsList.add("DET");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("N");

        InputData inputData = new InputData();
        inputData.setTokensList(tokensList);
        inputData.setTagsList(tagsList);


        capitalizedTokensProcessor.process(inputData);

        assertEquals(10, inputData.getTokensList().size());
        assertEquals(10, inputData.getTagsList().size());
        assertEquals("New York City Police Department", inputData.getTokensList().get(0));
        assertEquals("United States", inputData.getTokensList().get(7));
    }

    @Test
    public void test2() {
        List<String> tokensList = new ArrayList<>();
        tokensList.add("George");
        tokensList.add("Bush");
        tokensList.add("met");
        tokensList.add("Vladimir");
        tokensList.add("Putin");
        tokensList.add("in");
        tokensList.add("Bratislava");
        tokensList.add("in");
        tokensList.add("2005");

        List<String> tagsList = new ArrayList<>();
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("V");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("NR");

        InputData inputData = new InputData();
        inputData.setTokensList(tokensList);
        inputData.setTagsList(tagsList);

        capitalizedTokensProcessor.process(inputData);

        assertEquals(7, inputData.getTokensList().size());
        assertEquals(7, inputData.getTagsList().size());
        assertEquals("George Bush", inputData.getTokensList().get(0));
        assertEquals("Vladimir Putin", inputData.getTokensList().get(2));
        assertEquals("Bratislava", inputData.getTokensList().get(4));
    }

    @Test
    public void test3() {
        String sentence = "U.S. President Donald Trump told Russian President Vladimir Putin in a phone call on Monday that now is the time to work toward a peace agreement between Israelis and Palestinians, the White House said in a statement.";
        List<String> tagSequences = posTagger.tag(sentence);
        InputData inputData = inputDataFactory.create(sentence, tagSequences);
        capitalizedTokensProcessor.process(inputData);
        System.out.println(inputData.getTokensList().size());
    }
}
