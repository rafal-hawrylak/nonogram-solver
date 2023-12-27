package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

import java.util.List;

public class PuzzleCloner {

    public Puzzle clone(Puzzle puzzle) {
        List<List<Integer>> rows = clone(puzzle.rowsOrCols.stream().filter(rowOrCol -> rowOrCol.horizontal).toList());
        List<List<Integer>> cols = clone(puzzle.rowsOrCols.stream().filter(rowOrCol -> !rowOrCol.horizontal).toList());
        Puzzle newPuzzle = new Puzzle(puzzle.width, puzzle.height, rows, cols);
        newPuzzle.fields = clone(puzzle.fields);
        return newPuzzle;
    }

    private List<List<Integer>> clone(List<RowOrCol> rowOrCols) {
        return rowOrCols.stream()
                .map(rowOrCol -> rowOrCol.numbersToFind)
                .map(this::toList)
                .toList();
    }

    private List<Integer> toList(List<NumberToFind> numbers) {
        return numbers.stream().map(number -> number.number).toList();
    }

    private FieldState[][] clone(FieldState[][] fields) {
        FieldState[][] result = new FieldState[fields.length][fields[0].length];
        for (int r = 0; r < fields.length; r++) {
            result[r] = new FieldState[fields[r].length];
            for (int c = 0; c < fields[r].length; c++) {
                result[r][c] = fields[r][c];
            }
        }
        return result;
    }
}
