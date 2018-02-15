package com.aresWikipediaRelationsExtractor.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.Assert.assertTrue;

public class SpringConfigurationTest {

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        assertTrue(context != null);
    }
}
