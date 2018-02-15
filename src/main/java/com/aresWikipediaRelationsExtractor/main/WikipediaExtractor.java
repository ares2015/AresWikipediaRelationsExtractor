package com.aresWikipediaRelationsExtractor.main;

import java.io.IOException;

public interface WikipediaExtractor {

    void extract(String sourceDataDirectoryName) throws IOException;

}