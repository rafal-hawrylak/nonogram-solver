package org.hawrylak.puzzle.nonogram;

public class RowSelector {

    public RowOrCol find(Puzzle puzzle, int n, boolean horizontal) {
        return puzzle.rowsOrCols.stream().
            filter(rc -> rc.number == n).
            filter(rc -> rc.horizontal == horizontal)
            .findFirst().get();
    }
}
