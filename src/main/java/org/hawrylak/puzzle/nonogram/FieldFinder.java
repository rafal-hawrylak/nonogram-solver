package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.List;

public class FieldFinder {

    public boolean isFieldAtState(RowOrCol rowOrCol, Puzzle puzzle, int i, FieldState state) {
        if (rowOrCol.horizontal) {
            if (i >= 0 && i < puzzle.width) {
                return state.equals(puzzle.fields[i][rowOrCol.number]);
            }
        } else {
            if (i >= 0 && i < puzzle.height) {
                return state.equals(puzzle.fields[rowOrCol.number][i]);
            }
        }
        return false;
    }

    public List<Integer> findFieldsSetInGap(Gap gap, Puzzle puzzle, RowOrCol rowOrCol, FieldState fieldState) {
        var fieldPositions = new ArrayList<Integer>();
        var k = rowOrCol.number;
        for (int i = gap.start; i <= gap.end; i++) {
            var field = rowOrCol.horizontal ? puzzle.fields[i][k] : puzzle.fields[k][i];
            if (fieldState.equals(field)) {
                fieldPositions.add(i);
            }
        }
        return fieldPositions;
    }

}
