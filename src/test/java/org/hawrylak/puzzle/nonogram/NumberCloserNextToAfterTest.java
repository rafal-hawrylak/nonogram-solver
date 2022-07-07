package org.hawrylak.puzzle.nonogram;

import java.util.List;
import org.junit.jupiter.api.Test;

class NumberCloserNextToAfterTest extends PuzzleSolverTestBase {

    @Test
    void closeWithAfterEmptyLastGapOfBiggerSize() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■x";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfExactSizeOne() {
        String puzzleCase = "...x■";
        String expectedPuzzle = "...x■";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfMuchBiggerSize() {
        String puzzleCase = "...x■..........";
        String expectedPuzzle = "...x■x.........";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfMuchBiggerSize2() {
        String puzzleCase = "...x■........xx";
        String expectedPuzzle = "...x■x.......xx";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithBiggerNumberToFind() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■■";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithBiggerNumbersToFind() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■■";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithAmbiguousSituation() {
        String puzzleCase = "...x■....";
        String expectedPuzzle = "...x■....";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithAmbiguousSituation2() {
        String puzzleCase = ".x■....";
        String expectedPuzzle = ".x■....";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfBiggerSize() {
        String puzzleCase = "..■x..x.";
        String expectedPuzzle = ".x■x..x.";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfExactSizeOne() {
        String puzzleCase = "■x...";
        String expectedPuzzle = "■x...";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfMuchBiggerSize() {
        String puzzleCase = "..........■x...";
        String expectedPuzzle = ".........x■x...";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfMuchBiggerSize2() {
        String puzzleCase = "xx.........■x...";
        String expectedPuzzle = "xx........x■x...";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumberToFind() {
        String puzzleCase = ".■x...";
        String expectedPuzzle = "■■x...";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumbersToFind() {
        String puzzleCase = ".■x...";
        String expectedPuzzle = "■■x...";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumbersToFind2() {
        String puzzleCase = "..■x...";
        String expectedPuzzle = "..■x...";
        List<Integer> numbersToFind = List.of(1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithAmbiguousSituation() {
        String puzzleCase = "....■x...";
        String expectedPuzzle = "....■x...";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithAmbiguousSituation2() {
        String puzzleCase = "....■x.";
        String expectedPuzzle = "....■x.";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyOnlyNotFoundNumber() {
        String puzzleCase = "..x..x...■x...x..x..x";
        String expectedPuzzle = "..x..x.x■■x...x..x..x";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyOnlyNotFoundNumber2() {
        String puzzleCase = "..x■xx...■x...x..xx■x";
        String expectedPuzzle = "..x■xx.x■■x...x..xx■x";
        List<Integer> numbersToFind = List.of(1, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 19;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 19;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void productionCase1() {
        String puzzleCase = "■x....■x■.";
        String expectedPuzzle = "■x....■x■x";
        List<Integer> numbersToFind = List.of(1, 1, 1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 0;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void productionCase1Reversed() {
        String puzzleCase = ".■x■....x■";
        String expectedPuzzle = "x■x■x...x■";
        List<Integer> numbersToFind = List.of(1, 1, 1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(4).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(4).foundStart = 9;
        puzzle.rowsOrCols.get(0).numbersToFind.get(4).foundEnd = 9;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeWithOneEnd(puzzle, changes, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

}