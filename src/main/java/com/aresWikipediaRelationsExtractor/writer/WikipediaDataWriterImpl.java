package com.aresWikipediaRelationsExtractor.writer;

import com.aresWikipediaRelationsExtractor.data.WikipediaProcessingData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WikipediaDataWriterImpl implements WikipediaDataWriter {

    @Override
    public int write(String path, List<WikipediaProcessingData> wikipediaProcessingDataList, int id, boolean append) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, append);
            bw = new BufferedWriter(fw);
            for (WikipediaProcessingData wikipediaProcessingData : wikipediaProcessingDataList) {
                String newsDataRow = "";
                try {
                    newsDataRow = "\"" + id + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getAtomicSubject()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getExtendedSubject()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getAtomicVerbPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getExtendedVerbPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getAtomicNounPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getExtendedNounPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getSentence()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(wikipediaProcessingData.getObject()) + "\"";
                } catch (Exception e) {
                    continue;
                }
                bw.write(newsDataRow);
                id++;
//                System.out.println("Writing into WikipediaSemanticExtractionData file: " + newsDataRow);
                bw.newLine();
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