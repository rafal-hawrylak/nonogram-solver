package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ChangedInIteration {

    private final Puzzle puzzle;
    int iteration = 1;
    Set<RowOrCol> changedRowsOrCols = new HashSet<>();
    boolean[][] changedFields;

    public ChangedInIteration(Puzzle puzzle) {
        this.puzzle = puzzle;
        changedFields = new boolean[puzzle.width][puzzle.height];
    }

    public boolean anyChange() {
        return !changedRowsOrCols.isEmpty();
    }

    public boolean firstIteration() {
        return iteration == 1;
    }

    public void nextIteration(ChangedInIteration changesInCurrentIteration) {
        iteration++;
        clear();
        changedRowsOrCols.addAll(changesInCurrentIteration.changedRowsOrCols);
        changedFields = changesInCurrentIteration.changedFields;
        changesInCurrentIteration.clear();
        changesInCurrentIteration.iteration++;
    }

    public void clear() {
        changedRowsOrCols.clear();
        changedFields = new boolean[changedFields.length][changedFields[0].length];
    }

    public boolean hasChanged(RowOrCol rowOrCol) {
        return changedRowsOrCols.contains(rowOrCol);
    }

    public void markChangeSingle(int i, int j) {
        changedRowsOrCols.addAll(findPerpendicularRowOrCol(i, j));
        changedFields[i][j] = true;
    }

    public Collection<RowOrCol> findPerpendicularRowOrCol(int i, int j) {
        Collection<RowOrCol> rowsAndCols = new ArrayList<>();
        rowsAndCols.addAll(puzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == i)
            .filter(rc -> rc.horizontal)
            .toList());
        rowsAndCols.addAll(puzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == j)
            .filter(rc -> !rc.horizontal)
            .toList());
        return rowsAndCols;
    }
}
