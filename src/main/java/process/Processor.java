package process;

import model.ColumnsMap;
import model.ParsedString;
import model.TripleStringsSet;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Tom on 31.03.2017.
 */
public class Processor {

    private ColumnsMap columnsMap;

    public Processor() {
        columnsMap = new ColumnsMap();
    }

    public int countOfGroupsWithSizeOne(){
        return columnsMap.countOfGroupsWithSizeOne();
    }

    public List<Set<ParsedString>> performDivisionIntoDisjointGroups(Set<ParsedString> unGroupedUniqueLines) {
        columnsMap = buildColumnsMap(unGroupedUniqueLines);
        TripleStringsSet removed = new TripleStringsSet();
        List<Set<ParsedString>> disjointGroups = new LinkedList<>();
        for (Iterator<ParsedString> it = unGroupedUniqueLines.iterator(); it.hasNext(); ) {
            ParsedString currentString = it.next();
            it.remove();
            //Set<ParsedString> currentGroup = columnsMap.performDepthFirstSearch(currentString, removed);
            Set<ParsedString> currentGroup = columnsMap.performNonRecursiveDepthFirstSearch(currentString, removed);
            if (currentGroup.size() != 0)
                disjointGroups.add(currentGroup);
        }
        Collections.sort(disjointGroups, (o1, o2) -> o2.size() - o1.size());
        return disjointGroups;
    }

    private ColumnsMap buildColumnsMap(Set<ParsedString> unGroupedUniqueLines) {
        ColumnsMap columnsMap = new ColumnsMap();
        for (ParsedString line : unGroupedUniqueLines) {
            String cell1 = line.getA();
            if (!cell1.equals("")) {
                Collection<ParsedString> strings = columnsMap.getColumn1Map().get(cell1);
                if (strings != null)
                    strings.add(line);
                else {
                    List<ParsedString> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn1Map().put(cell1, ar);
                }
            }
            String cell2 = line.getB();
            if (!cell2.equals("")) {
                Collection<ParsedString> strings = columnsMap.getColumn2Map().get(cell2);
                if (strings != null)
                    strings.add(line);
                else {
                    List<ParsedString> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn2Map().put(cell2, ar);
                }
            }
            String cell3 = line.getC();
            if (!cell3.equals("")) {
                Collection<ParsedString> strings = columnsMap.getColumn3Map().get(cell3);
                if (strings != null)
                    strings.add(line);
                else {
                    List<ParsedString> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn3Map().put(cell3, ar);
                }
            }
        }
        return columnsMap;
    }
}
