package org.hawrylak.puzzle.nonogram.solver.utils;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class RowSelector {

    public RowOrCol find(Puzzle puzzle, int n, boolean horizontal) {
        return puzzle.rowsOrCols.stream().
            filter(rc -> rc.number == n).
            filter(rc -> rc.horizontal == horizontal)
            .findFirst().get();
    }

    public int countOfFields(Puzzle puzzle, RowOrCol rowOrCol, FieldState fieldState) {
        var count = 0;
        var limit = rowOrCol.horizontal ? puzzle.width : puzzle.height;
        for (int i = 0; i < limit; i++) {
            var field = rowOrCol.horizontal ? puzzle.fields[i][rowOrCol.number] : puzzle.fields[rowOrCol.number][i];
            if (fieldState.equals(field)) {
                count++;
            }
        }
        return count;
    }
}
