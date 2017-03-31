package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tom on 31.03.2017.
 */
public class RemovedSet {

    private Set<String> removedBy1Column;
    private Set<String> removedBy2Column;
    private Set<String> removedBy3Column;

    public RemovedSet() {
        removedBy1Column = new HashSet<>();
        removedBy2Column = new HashSet<>();
        removedBy3Column = new HashSet<>();
    }

    public Set<String> getRemovedBy1Column() {
        return removedBy1Column;
    }

    public Set<String> getRemovedBy2Column() {
        return removedBy2Column;
    }

    public Set<String> getRemovedBy3Column() {
        return removedBy3Column;
    }
}
