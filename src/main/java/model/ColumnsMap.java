package model;

import utils.SmallParser;

import java.util.*;

/**
 * Created by Tom on 31.03.2017.
 */
public class ColumnsMap {

    private int countOfGroupsWithSize1;

    Map<String, Collection<String>> column1Map;
    Map<String, Collection<String>> column2Map;
    Map<String, Collection<String>> column3Map;

    public ColumnsMap() {
        countOfGroupsWithSize1 = 0;
        column1Map = new HashMap<>();
        column2Map = new HashMap<>();
        column3Map = new HashMap<>();
    }

    public Map<String, Collection<String>> getColumn1Map() {
        return column1Map;
    }

    public Map<String, Collection<String>> getColumn2Map() {
        return column2Map;
    }

    public Map<String, Collection<String>> getColumn3Map() {
        return column3Map;
    }

    public int countOfGroupsWithSizeOne(){
        return countOfGroupsWithSize1;
    }

    public Set<String> performDepthFirstSearch(String currentString, RemovedSet removedLines){
        Set<String> currentGroup = new HashSet<>();
        boolean b1 = depthFirstBy1Column(currentGroup, currentString, removedLines);
        boolean b2 = depthFirstBy2Column(currentGroup, currentString, removedLines);
        boolean b3 = depthFirstBy3Column(currentGroup, currentString, removedLines);
        if (b1 && b2 && b3){
            currentGroup.add(currentString);
            countOfGroupsWithSize1++;
        }
        return currentGroup;
    }

    private boolean depthFirstBy1Column(Set<String> currentGroup, String currentString, RemovedSet removedSet) {
        boolean needToAddToGroup=false;
        List<String> items = SmallParser.getStringsList(currentString);
        Collection<String> strings = column1Map.get(items.get(0));
        if (!items.get(0).equals("")){
            boolean visited = !removedSet.getRemovedBy1Column().add(items.get(0));
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (String str : strings) {
                    List<String> stringsList = SmallParser.getStringsList(str);
                    if (!removedSet.getRemovedBy2Column().contains(stringsList.get(1)))
                        depthFirstBy2Column(currentGroup, str, removedSet);
                    if (!removedSet.getRemovedBy3Column().contains(stringsList.get(2)))
                        depthFirstBy3Column(currentGroup, str, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }
    private boolean depthFirstBy2Column(Set<String> currentGroup, String currentString, RemovedSet removedSet) {
        boolean needToAddToGroup=false;
        List<String> items = SmallParser.getStringsList(currentString);
        Collection<String> strings = column2Map.get(items.get(1));
        if (!items.get(1).equals("")){
            boolean visited = !removedSet.getRemovedBy2Column().add(items.get(1));
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (String str : strings) {
                    List<String> stringsList = SmallParser.getStringsList(str);
                    if (!removedSet.getRemovedBy1Column().contains(stringsList.get(0)))
                        depthFirstBy1Column(currentGroup, str, removedSet);
                    if (!removedSet.getRemovedBy3Column().contains(stringsList.get(2)))
                        depthFirstBy3Column(currentGroup, str, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }

    private boolean depthFirstBy3Column(Set<String> currentGroup, String currentString, RemovedSet removedSet) {
        boolean needToAddToGroup = true;
        List<String> items = SmallParser.getStringsList(currentString);
        Collection<String> strings = column3Map.get(items.get(2));
        if (!items.get(2).equals("")){
            boolean visited = !removedSet.getRemovedBy3Column().add(items.get(2));
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (String str : strings) {
                    List<String> stringsList = SmallParser.getStringsList(str);
                    if (!removedSet.getRemovedBy2Column().contains(stringsList.get(1)))
                        depthFirstBy2Column(currentGroup, str, removedSet);
                    if (!removedSet.getRemovedBy1Column().contains(stringsList.get(0)))
                        depthFirstBy1Column(currentGroup, str, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }
}
