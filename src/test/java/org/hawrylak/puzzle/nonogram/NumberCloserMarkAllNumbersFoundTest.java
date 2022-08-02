package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.junit.jupiter.api.Test;

public class NumberCloserMarkAllNumbersFoundTest extends PuzzleSolverTestBase {

    @Test
    void markSingle() {
        String puzzleCase = "■x";
        String expectedPuzzle = "■x";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertEachNumberIsFound(puzzle.rowsOrCols);
    }

    @Test
    void markTwoOnes() {
        String puzzleCase = "■x■";
        String expectedPuzzle = "■x■";
        List<Integer> numbersToFind = List.of(1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAllTheGapsIfAllFullMarked(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertEachNumberIsFound(puzzle.rowsOrCols);
    }

    private void assertEachNumberIsFound(List<RowOrCol> rowsOrCols) {
        for (RowOrCol rowOrCol : rowsOrCols) {
            for (NumberToFind numberToFind : rowOrCol.numbersToFind) {
                assertTrue(numberToFind.found);
            }
        }
    }
}
