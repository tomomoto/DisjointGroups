package process;

import model.ColumnsMap;
import model.RemovedSet;
import utils.SmallParser;

import java.util.*;

/**
 * Created by Tom on 31.03.2017.
 */
public class Processor {

    private ColumnsMap columnsMap;
    private RemovedSet removedSet;

    public Processor() {
        columnsMap = new ColumnsMap();
        removedSet = new RemovedSet();
    }

    public int countOfGroupsWithSizeOne(){
        return columnsMap.countOfGroupsWithSizeOne();
    }

    public List<Set<String>> performDivisionIntoDisjointGroups(Set<String> unGroupedUniqueLines) {
        columnsMap = buildColumnsMap(unGroupedUniqueLines);
        List<Set<String>> disjointGroups = new LinkedList<>();
        for (Iterator<String> it = unGroupedUniqueLines.iterator(); it.hasNext(); ) {
            String currentString = it.next();
            it.remove();
            Set<String> currentGroup = columnsMap.performDepthFirstSearch(currentString, unGroupedUniqueLines, removedSet);
            if (currentGroup.size() != 0)
                disjointGroups.add(currentGroup);
        }
        Collections.sort(disjointGroups, (o1, o2) -> o2.size() - o1.size());
        return disjointGroups;
    }

    private ColumnsMap buildColumnsMap(Set<String> ungroupedUniqueLines) {
        ColumnsMap columnsMap = new ColumnsMap();
        for (String line : ungroupedUniqueLines) {
            List<String> items = SmallParser.getStringsList(line);
            String cell1 = items.get(0);
            if (!cell1.equals("")) {
                Collection<String> strings = columnsMap.getColumn1Map().get(cell1);
                if (strings != null)
                    strings.add(line);
                else {
                    ArrayList<String> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn1Map().put(cell1, ar);
                }
            }
            String cell2 = items.get(1);
            if (!cell2.equals("")) {
                Collection<String> strings = columnsMap.getColumn2Map().get(cell2);
                if (strings != null)
                    strings.add(line);
                else {
                    ArrayList<String> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn2Map().put(cell2, ar);
                }
            }
            String cell3 = items.get(2);
            if (!cell3.equals("")) {
                Collection<String> strings = columnsMap.getColumn3Map().get(cell3);
                if (strings != null)
                    strings.add(line);
                else {
                    ArrayList<String> ar = new ArrayList<>();
                    ar.add(line);
                    columnsMap.getColumn3Map().put(cell3, ar);
                }
            }
        }
        return columnsMap;
    }
}
