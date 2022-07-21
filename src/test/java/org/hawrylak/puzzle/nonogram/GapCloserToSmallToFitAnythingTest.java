package org.hawrylak.puzzle.nonogram;

import java.util.List;
import org.junit.jupiter.api.Test;

public class GapCloserToSmallToFitAnythingTest extends PuzzleSolverTestBase {

    @Test
    void closeTooSmallToFitAnythingSingleBigGapWhenNumberNotFound() {
        String puzzleCase = "■.......";
        String expectedPuzzle = "■.......";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void doNotCloseEqualToFit() {
        String puzzleCase = "■xx....x";
        String expectedPuzzle = "■xx....x";
        List<Integer> numbersToFind = List.of(1, 4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void doNotCloseEqualToFitTwoGaps() {
        String puzzleCase = "■xx....x....x";
        String expectedPuzzle = "■xx....x....x";
        List<Integer> numbersToFind = List.of(1, 4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAlmostEqualToFit() {
        String puzzleCase = "■xx....x.....x";
        String expectedPuzzle = "■xxxxxxx.....x";
        List<Integer> numbersToFind = List.of(1, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAlmostEqualToFit2() {
        String puzzleCase = "■xx.....x....x";
        String expectedPuzzle = "■xx.....xxxxxx";
        List<Integer> numbersToFind = List.of(1, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapWhenNumbersNotFound() {
        String puzzleCase = "■...■..■■";
        String expectedPuzzle = "■...■..■■";
        List<Integer> numbersToFind = List.of(1, 1, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleSmallGap() {
        String puzzleCase = "■.";
        String expectedPuzzle = "■x";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGap() {
        String puzzleCase = "■.......";
        String expectedPuzzle = "■xxxxxxx";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleSmallGaReversed() {
        String puzzleCase = ".■";
        String expectedPuzzle = "x■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapReversed() {
        String puzzleCase = ".......■";
        String expectedPuzzle = "xxxxxxx■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapAndSingleSmall() {
        String puzzleCase = "■.....x.";
        String expectedPuzzle = "■xxxxxxx";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapAndSingleSmall2() {
        String puzzleCase = "■.x.....";
        String expectedPuzzle = "■xxxxxxx";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingTwoBigGaps() {
        String puzzleCase = "■.....x.......";
        String expectedPuzzle = "■xxxxxxxxxxxxx";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapAndSingleSmallReversed() {
        String puzzleCase = ".x.....■";
        String expectedPuzzle = "xxxxxxx■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingSingleBigGapAndSingleSmallReversed2() {
        String puzzleCase = ".....x.■";
        String expectedPuzzle = "xxxxxxx■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeTooSmallToFitAnythingTwoBigGapsReversed() {
        String puzzleCase = "......x......■";
        String expectedPuzzle = "xxxxxxxxxxxxx■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        gapCloser.closeTooSmallToFitAnything(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }
}
