package org.hawrylak.puzzle.nonogram;

import java.util.HashSet;
import java.util.Set;

public class ChangedInLastIteration {

    int iteration = 1;
    Set<RowOrCol> changedRowsOrCols = new HashSet<>();
    boolean[][] changedFields;

    public ChangedInLastIteration(int width, int height) {
        changedFields = new boolean[width][height];
    }

    public boolean anyChange() {
        return !changedRowsOrCols.isEmpty();
    }

    public boolean firstIteration() {
        return iteration == 1;
    }

    public void nextIteration() {
        iteration++;
    }

    public void clear() {
        changedRowsOrCols.clear();
        changedFields = new boolean[changedFields.length][changedFields[0].length];
    }

    public boolean hasChanged(RowOrCol rowOrCol) {
        return changedRowsOrCols.contains(rowOrCol);
    }

    public void markChange(RowOrCol rowOrCol, int i, int j) {
        changedRowsOrCols.add(rowOrCol);
        changedFields[i][j] = true;
    }
}
