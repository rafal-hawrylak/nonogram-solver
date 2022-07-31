package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.Test;

public class GapFillerFillTheGapPartiallyForNNumbersTest extends PuzzleSolverTestBase {

    @Test
    void testCase1() {
        String puzzleCase = "■■■■■xxxx■■■■■x.■...■....";
        String expectedPuzzle = "■■■■■xxxx■■■■■x■■■■x■....";
        List<Integer> numbersToFind = List.of(5, 5, 4, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        var rowOrCol = puzzle.rowsOrCols.get(0);
        var gap = gapFinder.find(puzzle, rowOrCol).get(2);
        var numbers = rowOrCol.numbersToFind.subList(2, 4);
        gapFiller.fillTheGapPartiallyForNNumbers(gap, numbers, 7, rowOrCol, puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }

    @Test
    void testCase2() {
        String puzzleCase = ".■■■■■..■.x■■xx■■xx■";
        String expectedPuzzle = ".■■■■■..■.x■■xx■■xx■";
        List<Integer> numbersToFind = List.of(6, 2, 2, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        var rowOrCol = puzzle.rowsOrCols.get(0);
        var gap = gapFinder.find(puzzle, rowOrCol).get(0);
        var numbers = rowOrCol.numbersToFind.subList(0, 2);
        gapFiller.fillTheGapPartiallyForNNumbers(gap, numbers, 7, rowOrCol, puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        puzzle.rowsOrCols.get(0).numbersToFind.forEach(n -> assertFalse(n.found));
    }
}
