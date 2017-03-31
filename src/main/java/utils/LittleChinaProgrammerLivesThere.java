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
        System.out.print("Start parsing.");
        long startParsing = System.currentTimeMillis();
        //TreeMap<Integer,String> hashMapInvalidStrings = new TreeMap<>();
        Set<String> invalidStrings = new HashSet<>();
        Set<String> unGroupedUniqueLines = getUniqueLines(filePath, invalidStrings);
        long finishParsing = System.currentTimeMillis();
        float timeConsumedParsingSec = ((finishParsing - startParsing)/1000)%60;
        System.out.println(" Time = " + timeConsumedParsingSec + " seconds");
        Processor processor = new Processor();
        System.out.print("Start processing.");
        long startProcessing = System.currentTimeMillis();
        List<Set<String>> disjointGroups = processor.performDivisionIntoDisjointGroups(unGroupedUniqueLines);
        long finishProcessing = System.currentTimeMillis();
        float timeConsumedProcessingSec = ((finishProcessing - startProcessing)/1000)%60;
        //Выключил для повышения производительности
        //printStatistics(hashMapInvalidStrings);
        System.out.println(" Time: " + timeConsumedProcessingSec + " seconds");
        System.out.print("Start output.");
        long startOutput = System.currentTimeMillis();
        saveResultToFile(processor, disjointGroups);
        long finishOutput = System.currentTimeMillis();
        float timeConsumedSec = ((finishOutput - startOutput)/1000)%60;
        System.out.println(" Time: " + timeConsumedSec + " seconds");
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
                                              //Map<Integer, String> mapInvalidStrings
                                              Set<String> mapInvalidStrings) {
        Set<String> uniqueLines = new HashSet<>();
        BufferedReader br = getBufferedReader(filePath);
        parsing(uniqueLines, br, mapInvalidStrings);
        bufferCloser(br);
        return uniqueLines;
    }

    private static void parsing(Set<String> uniqueLines, BufferedReader br,
                                //Map<Integer,String> mapInvalidStrings
                                Set<String> mapInvalidStrings) {
        String line;
        int i = 1;
        try {
            while ((line = br.readLine()) != null) {
                try {
                    String parsedString = SmallParser.parse(line);
                    boolean add = uniqueLines.add(parsedString);
                    if (!add) {
                        //Если невалидных строк очень много или вылетает исключание аутофмемориэррор,
                        //Отключите добавление невалида для улучшения производительности
                        //Здесь невалид строка является дублем.
                        //mapInvalidStrings.put(i,"Duplicate: "+line);
                        //mapInvalidStrings.add(line);
                    }
                } catch (InvalidStringException e) {
                    //Здесь невалид строка не прошла парсинг.
                    //mapInvalidStrings.add(line);
                   // mapInvalidStrings.put(i,"Invalid line: "+line);
                }
                i++;
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

    public static void saveResultToFile(Processor proc, List<Set<String>> disjointGroups){
        try(PrintWriter out = new PrintWriter("processResult.txt")){
            int i = countGroupsWithMoreThanOneElement(proc, disjointGroups);
            out.println("-------------------------------------------");
            out.println("Groups with more than one row : "+i);
            out.println("-------------------------------------------");
            saveGroupsToFile(disjointGroups, out);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private static void saveGroupsToFile(List<Set<String>> disjointGroups, PrintWriter out) {
        int i=1;
        for (Iterator<Set<String>> iterator = disjointGroups.iterator(); iterator.hasNext();) {
            out.println("Group "+i);
            Set<String> group = iterator.next();
            for (String string:group){
                out.println(string);
            }
            i++;
            iterator.remove();
            out.println("-------------------------------------------");
        }
    }

    private static int countGroupsWithMoreThanOneElement (Processor proc, List<Set<String>> disjointGroups){
        return disjointGroups.size() - proc.countOfGroupsWithSizeOne();
    }

}
