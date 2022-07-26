package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class ChangedInIteration {

    private final Puzzle puzzle;
    @Getter
    int iteration = 0;
    @Getter
    boolean debug;
    Set<RowOrCol> changedRowsOrCols = new HashSet<>();
    boolean[][] changedFields;

    public ChangedInIteration(Puzzle puzzle) {
        this(puzzle, false);
    }

    public ChangedInIteration(Puzzle puzzle, boolean debug) {
        this.puzzle = puzzle;
        this.debug = debug;
        changedFields = new boolean[puzzle.width][puzzle.height];
    }

    public boolean anyChange() {
        return !changedRowsOrCols.isEmpty();
    }

    public boolean firstIteration() {
        return iteration <= 1;
    }

    public void nextIteration() {
        iteration++;
        clear();
    }

    public void clear() {
        changedRowsOrCols.clear();
        changedFields = new boolean[changedFields.length][changedFields[0].length];
    }

    public boolean hasChanged(RowOrCol rowOrCol) {
        return changedRowsOrCols.contains(rowOrCol);
    }

    public void markChangeSingle(int c, int r) {
        changedRowsOrCols.addAll(findPerpendicularRowOrCol(c, r));
        changedFields[c][r] = true;
    }

    public Collection<RowOrCol> findPerpendicularRowOrCol(int c, int r) {
        Collection<RowOrCol> rowsAndCols = new ArrayList<>();
        rowsAndCols.addAll(puzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == r)
            .filter(rc -> rc.horizontal)
            .toList());
        rowsAndCols.addAll(puzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == c)
            .filter(rc -> !rc.horizontal)
            .toList());
        return rowsAndCols;
    }

    public boolean debugModeAndChangesDone() {
        return debug && anyChange();
    }
}
