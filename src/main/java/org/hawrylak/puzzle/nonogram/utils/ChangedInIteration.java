package org.hawrylak.puzzle.nonogram.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ChangedInIteration {

    private final Puzzle currentPuzzle;
    private Puzzle previousPuzzle;
    int iteration = 0;
    private final Set<RowOrCol> changedRowsOrCols = new HashSet<>();
    private boolean[][] changedFields;
    private final Set<NumberToFind> changedNumbers = new HashSet<>();
    @Getter(AccessLevel.PRIVATE)
    private PuzzleCloner puzzleCloner = new PuzzleCloner();

    public ChangedInIteration(Puzzle currentPuzzle) {
        this.currentPuzzle = currentPuzzle;
        this.previousPuzzle = puzzleCloner.clone(this.currentPuzzle);
        changedFields = new boolean[currentPuzzle.width][currentPuzzle.height];
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

    public void markChange(RowOrCol rowOrCol) {
        changedRowsOrCols.add(rowOrCol);
    }

    public void markChangeField(int c, int r) {
        changedRowsOrCols.addAll(findPerpendicularRowOrCol(c, r));
        changedFields[c][r] = true;
    }

    public void markChangeNumber(RowOrCol rowOrCol, NumberToFind number) {
        changedRowsOrCols.add(rowOrCol);
        changedNumbers.add(number);
    }

    public Collection<RowOrCol> findPerpendicularRowOrCol(int c, int r) {
        Collection<RowOrCol> rowsAndCols = new ArrayList<>();
        rowsAndCols.addAll(currentPuzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == r)
            .filter(rc -> rc.horizontal)
            .toList());
        rowsAndCols.addAll(currentPuzzle.rowsOrCols.stream()
            .filter(rc -> rc.number == c)
            .filter(rc -> !rc.horizontal)
            .toList());
        return rowsAndCols;
    }

    public void rememberPreviousPuzzle() {
        this.previousPuzzle = puzzleCloner.clone(this.currentPuzzle);
    }

}
