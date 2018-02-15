package com.aresWikipediaRelationsExtractor.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> getFileNames(String directoryName) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(directoryName);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().contains("WikipediaRawTextData")) {
                fileNames.add(directoryName + "\\" + "\\" + file.getName());
            }
        }
        return fileNames;
    }

    public static List<String> getFileContent(String path) {
        List<String> fileRows = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String row = br.readLine();
            while (row != null) {
                try {
                    fileRows.add(row);
                    row = br.readLine();
                } catch (Exception e) {
                    row = br.readLine();
                    continue;
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return fileRows;
    }
}
