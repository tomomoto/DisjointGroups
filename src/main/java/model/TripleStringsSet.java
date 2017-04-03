package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 31.03.2017.
 */
public class TripleStringsSet {

    private Set<String> Column1StringsSet;
    private Set<String> Column2StringsSet;
    private Set<String> Column3StringsSet;

    public TripleStringsSet(ParsedString parsedString) {
        Column1StringsSet = new HashSet<>();
        Column2StringsSet = new HashSet<>();
        Column3StringsSet = new HashSet<>();
        String a = parsedString.getA();
        String b = parsedString.getB();
        String c = parsedString.getC();
        if (!a.equals("")) Column1StringsSet.add(a);
        if (!b.equals("")) Column2StringsSet.add(b);
        if (!c.equals("")) Column3StringsSet.add(c);
    }

    public TripleStringsSet() {
        Column1StringsSet = new HashSet<>();
        Column2StringsSet = new HashSet<>();
        Column3StringsSet = new HashSet<>();
    }

    public boolean isEmpty (){
        boolean emptyBy1Column = Column1StringsSet.isEmpty();
        boolean emptyBy2Column = Column2StringsSet.isEmpty();
        boolean emptyBy3Column = Column3StringsSet.isEmpty();
        if (emptyBy1Column && emptyBy2Column && emptyBy3Column)
            return true;
        return false;
    }

    public Set<String> getColumn1StringsSet() {
        return Column1StringsSet;
    }

    public Set<String> getColumn2StringsSet() {
        return Column2StringsSet;
    }

    public Set<String> getColumn3StringsSet() {
        return Column3StringsSet;
    }
}
