package utils;

import model.ColumnsMap;
import model.RemovedSet;
import process.Processor;

import java.io.*;
import java.util.*;

/**
 * Created by Tom on 26.03.2017.
 */
public class LittleChinaProgrammerLivesThere {

    public static void wakeUpAndWork(String filePath) {
        System.out.println("Starting parsing and processing...");
        long start = System.currentTimeMillis();

        //TreeMap<Integer,String> hashMapInvalidStrings = new TreeMap<>();
        Set<String> invalidStrings = new HashSet<>();
        Set<String> unGroupedUniqueLines = getUniqueLines(filePath, invalidStrings);
        Processor processor = new Processor();
        List<Set<String>> disjointGroups = processor.performDivisionIntoDisjointGroups(unGroupedUniqueLines);
        System.out.println(disjointGroups.size());

        long finish = System.currentTimeMillis();
        int size = invalidStrings.size();
        float timeConsumedSec = (finish - start)/1000;
        //printStatistics(hashMapInvalidStrings);
        System.out.println("Parsing and process execution time: " + timeConsumedSec + " seconds");
    }

    private static void printStatistics(Map<Integer, String> mapInvalidStrings) {
        System.out.println("------------------------------------------------------------");
        System.out.println("Invalid string statistic with given format [index] = string");
        printMap(mapInvalidStrings);
        System.out.println("------------------------------------------------------------");
    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("["+pair.getKey()+"]" + " = " + pair.getValue());
            it.remove();
        }
    }

    private static Set<String> getUniqueLines(String filePath,
                                              Set<String> mapInvalidStrings
                                              //Map<Integer, String> mapInvalidStrings
    ) {
        Set<String> uniqueLines = new HashSet<>();
        BufferedReader br = getBufferedReader(filePath);
        parsing(uniqueLines, br, mapInvalidStrings);
        bufferCloser(br);
        return uniqueLines;
    }

    private static void parsing(Set<String> uniqueLines, BufferedReader br,
                                //Map<Integer,String> mapInvalidStrings
                                Set<String> mapInvalidStrings
    ) {
        String line;
        int i = 1;
        try {
            while ((line = br.readLine()) != null) {
                try {
                    String parsedString = SmallParser.parse(line);
                    boolean add = uniqueLines.add(parsedString);
                    if (add == false) {
                        //Если невалидных строк очень много или вылетает исключание аутофмемориэррор,
                        //Отключите добавление невалида для улучшения производительности
                        //mapInvalidStrings.put(i,"Duplicate: "+line);
                        //mapInvalidStrings.add(line);
                    }
                } catch (InvalidStringException e) {
                    //mapInvalidStrings.add(line);
                   // mapInvalidStrings.put(i,"Invalid line: "+line);
                }
                i++;
                //System.out.println(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferCloser(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedReader getBufferedReader(String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }
}
