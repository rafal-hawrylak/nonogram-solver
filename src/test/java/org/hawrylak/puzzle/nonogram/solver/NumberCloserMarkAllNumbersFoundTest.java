package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberCloserMarkAllNumbersFoundTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new CloseAllTheGapsIfAllFullMarked(gapFinder, rowSelector, gapCloser);
    }

    @Test
    void markSingle() {
        String puzzleCase = "■x";
        String expectedPuzzle = "■x";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
        assertEachNumberIsFound(puzzle.rowsOrCols);
    }

    @Test
    void markTwoOnes() {
        String puzzleCase = "■x■";
        String expectedPuzzle = "■x■";
        List<Integer> numbersToFind = List.of(1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
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
