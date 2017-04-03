package model;

import java.util.*;

/**
 * Created by Tom on 31.03.2017.
 */
public class ColumnsMap {

    private int countOfGroupsWithSize1;

    Map<String, Collection<ParsedString>> column1Map;
    Map<String, Collection<ParsedString>> column2Map;
    Map<String, Collection<ParsedString>> column3Map;

    public ColumnsMap() {
        countOfGroupsWithSize1 = 0;
        column1Map = new HashMap<>();
        column2Map = new HashMap<>();
        column3Map = new HashMap<>();
    }

    public Map<String, Collection<ParsedString>> getColumn1Map() {
        return column1Map;
    }

    public Map<String, Collection<ParsedString>> getColumn2Map() {
        return column2Map;
    }

    public Map<String, Collection<ParsedString>> getColumn3Map() {
        return column3Map;
    }

    public int countOfGroupsWithSizeOne(){
        return countOfGroupsWithSize1;
    }

    public Set<ParsedString> performRecursiveDepthFirstSearch(ParsedString currentString, TripleStringsSet removedLines){
        Set<ParsedString> currentGroup = new HashSet<>();
        boolean b1 = depthFirstBy1Column(currentGroup, currentString, removedLines);
        boolean b2 = depthFirstBy2Column(currentGroup, currentString, removedLines);
        boolean b3 = depthFirstBy3Column(currentGroup, currentString, removedLines);
        if (b1 && b2 && b3){
            currentGroup.add(currentString);
            countOfGroupsWithSize1++;
        }
        return currentGroup;
    }

    public Set<ParsedString> performNonRecursiveDepthFirstSearch(ParsedString currentString, TripleStringsSet removedLines){
        TripleStringsSet visitedElements = new TripleStringsSet();
        Set<ParsedString> currentGroup = new HashSet<>();
        TripleStringsSet needToProcessSet = new TripleStringsSet(currentString);
        boolean b1=true,b2=true,b3=true;
        while (!needToProcessSet.isEmpty()) {
            if (needToProcessSet.getColumn1StringsSet().iterator().hasNext())
                b1 = searchBy1Column(currentGroup, needToProcessSet.getColumn1StringsSet().iterator().next(), removedLines, needToProcessSet, visitedElements);
            if (needToProcessSet.getColumn2StringsSet().iterator().hasNext())
                b2 = searchBy2Column(currentGroup, needToProcessSet.getColumn2StringsSet().iterator().next(), removedLines, needToProcessSet, visitedElements);
            if (needToProcessSet.getColumn3StringsSet().iterator().hasNext())
                b3 = searchBy3Column(currentGroup, needToProcessSet.getColumn3StringsSet().iterator().next(), removedLines, needToProcessSet, visitedElements);
        }
        if (b1 && b2 && b3 && currentGroup.add(currentString)) {
                countOfGroupsWithSize1++;
        }
        return currentGroup;
    }

    private boolean depthFirstBy1Column(Set<ParsedString> currentGroup, ParsedString currentString, TripleStringsSet removedSet) {
        boolean needToAddToGroup=false;
        Collection<ParsedString> strings = column1Map.get(currentString.getA());
        if (!currentString.getA().equals("")){
            boolean visited = !removedSet.getColumn1StringsSet().add(currentString.getA());
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (ParsedString parsedString : strings) {
                    if (!removedSet.getColumn2StringsSet().contains(parsedString.getB()))
                        depthFirstBy2Column(currentGroup, parsedString, removedSet);
                    if (!removedSet.getColumn3StringsSet().contains(parsedString.getC()))
                        depthFirstBy3Column(currentGroup, parsedString, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }

    private boolean depthFirstBy2Column(Set<ParsedString> currentGroup, ParsedString currentString, TripleStringsSet removedSet) {
        boolean needToAddToGroup=false;
        Collection<ParsedString> strings = column2Map.get(currentString.getB());
        if (!currentString.getB().equals("")){
            boolean visited = !removedSet.getColumn2StringsSet().add(currentString.getB());
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (ParsedString parsedString : strings) {
                    if (!removedSet.getColumn1StringsSet().contains(parsedString.getA()))
                        depthFirstBy1Column(currentGroup, parsedString, removedSet);
                    if (!removedSet.getColumn3StringsSet().contains(parsedString.getC()))
                        depthFirstBy3Column(currentGroup, parsedString, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }

    private boolean depthFirstBy3Column(Set<ParsedString> currentGroup, ParsedString currentString, TripleStringsSet removedSet) {
        boolean needToAddToGroup = true;
        Collection<ParsedString> strings = column3Map.get(currentString.getC());
        if (!currentString.getC().equals("")){
            boolean visited = !removedSet.getColumn3StringsSet().add(currentString.getC());
            if (!visited) needToAddToGroup = true;
            if (strings != null && !visited && strings.size() != 1) {
                currentGroup.addAll(strings);
                needToAddToGroup = false;
                for (ParsedString parsedString : strings) {
                    if (!removedSet.getColumn2StringsSet().contains(parsedString.getB()))
                        depthFirstBy2Column(currentGroup, parsedString, removedSet);
                    if (!removedSet.getColumn1StringsSet().contains(parsedString.getA()))
                        depthFirstBy1Column(currentGroup, parsedString, removedSet);
                }
            }
        }
        return needToAddToGroup;
    }

    private boolean searchBy1Column(Set<ParsedString> currentGroup, String currentColumn1String, TripleStringsSet removedSet, TripleStringsSet needToProcess, TripleStringsSet alreadyProcessed) {
        boolean needToAddToGroup=false;
        Collection<ParsedString> strings = column1Map.get(currentColumn1String);
        if (!currentColumn1String.equals("")){
            boolean containInRemovedSet = !removedSet.getColumn1StringsSet().add(currentColumn1String);
            if (!containInRemovedSet) {
                needToAddToGroup = true;
                if (strings != null && strings.size() != 1 && !alreadyProcessed.getColumn1StringsSet().contains(currentColumn1String)) {
                    currentGroup.addAll(strings);
                    needToAddToGroup = false;
                    for (ParsedString parsedString : strings) {
                        if (!parsedString.getB().equals("") && !removedSet.getColumn2StringsSet().contains(parsedString.getB()))
                            needToProcess.getColumn2StringsSet().add(parsedString.getB());
                        if (!parsedString.getC().equals("") && !removedSet.getColumn3StringsSet().contains(parsedString.getC()))
                            needToProcess.getColumn3StringsSet().add(parsedString.getC());
                    }
                }
            }
            needToProcess.getColumn1StringsSet().remove(currentColumn1String);
            alreadyProcessed.getColumn1StringsSet().add(currentColumn1String);
        }
        return needToAddToGroup;
    }

    private boolean searchBy2Column(Set<ParsedString> currentGroup, String currentColumn2String, TripleStringsSet removedSet, TripleStringsSet setToVisit, TripleStringsSet visitedLines) {
        boolean needToAddToGroup=false;
        Collection<ParsedString> strings = column2Map.get(currentColumn2String);
        if (!currentColumn2String.equals("")){
            boolean containInRemovedSet = !removedSet.getColumn2StringsSet().add(currentColumn2String);
            if (!containInRemovedSet){
                needToAddToGroup = true;
                if (strings != null && strings.size() != 1  && !visitedLines.getColumn2StringsSet().contains(currentColumn2String)) {
                    currentGroup.addAll(strings);
                    needToAddToGroup = false;
                    for (ParsedString parsedString : strings) {
                        if (!parsedString.getA().equals("") && !removedSet.getColumn1StringsSet().contains(parsedString.getA()))
                            setToVisit.getColumn1StringsSet().add(parsedString.getA());
                        if (!parsedString.getC().equals("") && !removedSet.getColumn3StringsSet().contains(parsedString.getC()))
                            setToVisit.getColumn3StringsSet().add(parsedString.getC());
                    }
                }
            }
            setToVisit.getColumn2StringsSet().remove(currentColumn2String);
            visitedLines.getColumn2StringsSet().add(currentColumn2String);

        }
        return needToAddToGroup;
    }

    private boolean searchBy3Column(Set<ParsedString> currentGroup, String currentColumn3String, TripleStringsSet removedSet, TripleStringsSet setToVisit, TripleStringsSet visitedLines) {
        boolean needToAddToGroup=false;
        Collection<ParsedString> strings = column3Map.get(currentColumn3String);
        if (!currentColumn3String.equals("")){
            boolean containInRemovedSet = !removedSet.getColumn3StringsSet().add(currentColumn3String);
            if (!containInRemovedSet){
                needToAddToGroup = true;
                if (strings != null && strings.size() != 1  && !visitedLines.getColumn3StringsSet().contains(currentColumn3String)) {
                    currentGroup.addAll(strings);
                    needToAddToGroup = false;
                    for (ParsedString parsedString : strings) {
                        if (!parsedString.getA().equals("") && !removedSet.getColumn1StringsSet().contains(parsedString.getA()))
                            setToVisit.getColumn1StringsSet().add(parsedString.getA());
                        if (!parsedString.getB().equals("") && !removedSet.getColumn2StringsSet().contains(parsedString.getB()))
                            setToVisit.getColumn2StringsSet().add(parsedString.getB());
                    }
                }
            }
            setToVisit.getColumn3StringsSet().remove(currentColumn3String);
            visitedLines.getColumn3StringsSet().add(currentColumn3String);
        }
        return needToAddToGroup;
    }

}
