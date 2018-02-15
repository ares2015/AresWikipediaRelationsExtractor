package com.aresWikipediaRelationsExtractor.main;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AresWikipediaRelationsExtractor {

    private static final String SOURCE_DATA_DIRECTORY = "c:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\backup\\WikipediaRawTextData\\01_09_2017";

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        WikipediaExtractor wikipediaExtractor = (WikipediaExtractor) context.getBean("wikipediaExtractor");

        wikipediaExtractor.extract(SOURCE_DATA_DIRECTORY);

    }

}