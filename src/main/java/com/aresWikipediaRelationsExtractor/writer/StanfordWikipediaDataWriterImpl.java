package com.aresWikipediaRelationsExtractor.writer;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;
import edu.stanford.nlp.ie.util.RelationTriple;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StanfordWikipediaDataWriterImpl implements StanfordWikipediaDataWriter {

    @Override
    public int write(String path, List<WikipediaProcessingData> wikipediaProcessingDataList, int id, boolean append) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, append);
            bw = new BufferedWriter(fw);
            for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
                if (wikipediaProcessingData.isStanfordCorrectRelation()) {
                    String newsDataRow = "";
                    try {
                        newsDataRow = "\"" + id + "\"" + "," +
                                "\"" + removeDoubleQuotes(wikipediaProcessingData.getStanfordSubject()) + "\"" + "," +
                                "\"" + removeDoubleQuotes(wikipediaProcessingData.getStanfordRelation()) + "\"" + "," +
                                "\"" + removeDoubleQuotes(wikipediaProcessingData.getStanfordObject()) + "\"" + "," +
                                "\"" + removeDoubleQuotes(wikipediaProcessingData.getSentence()) + "\"" + "," +
                                "\"" + removeDoubleQuotes(wikipediaProcessingData.getObject()) + "\"";
                    } catch (Exception e) {
                        continue;
                    }
                    bw.write(newsDataRow);
                    id++;
                    bw.newLine();
                }
            }
            System.out.println("Writing into news data file finished");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }

    private String removeDoubleQuotes(String string) {
        if (string.contains("\"")) {
            string = string.replace("\"", "");
        }
        return string;
    }

}
