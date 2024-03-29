package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CloseWithOneEndTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new CloseWithOneEnd(gapFinder, numberSelector, gapFiller);
    }

    @Test
    void closeWithAfterEmptyLastGapOfBiggerSize() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■x";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfExactSizeOne() {
        String puzzleCase = "...x■";
        String expectedPuzzle = "...x■";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfMuchBiggerSize() {
        String puzzleCase = "...x■..........";
        String expectedPuzzle = "...x■x.........";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapOfMuchBiggerSize2() {
        String puzzleCase = "...x■........xx";
        String expectedPuzzle = "...x■x.......xx";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithBiggerNumberToFind() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■■";
        List<Integer> numbersToFind = List.of(2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithBiggerNumbersToFind() {
        String puzzleCase = "...x■.";
        String expectedPuzzle = "...x■■";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithAmbiguousSituation() {
        String puzzleCase = "...x■....";
        String expectedPuzzle = "...x■■...";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithAfterEmptyLastGapWithAmbiguousSituation2() {
        String puzzleCase = ".x■....";
        String expectedPuzzle = ".x■■...";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfBiggerSize() {
        String puzzleCase = "..■x..x.";
        String expectedPuzzle = ".x■x..x.";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfExactSizeOne() {
        String puzzleCase = "■x...";
        String expectedPuzzle = "■x...";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfMuchBiggerSize() {
        String puzzleCase = "..........■x...";
        String expectedPuzzle = ".........x■x...";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapOfMuchBiggerSize2() {
        String puzzleCase = "xx.........■x...";
        String expectedPuzzle = "xx........x■x...";
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumberToFind() {
        String puzzleCase = ".■x...";
        String expectedPuzzle = "■■x...";
        List<Integer> numbersToFind = List.of(2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumbersToFind() {
        String puzzleCase = ".■x...";
        String expectedPuzzle = "■■x...";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithBiggerNumbersToFind2() {
        String puzzleCase = "..■x...";
        String expectedPuzzle = "..■x...";
        List<Integer> numbersToFind = List.of(1, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithAmbiguousSituation() {
        String puzzleCase = "....■x...";
        String expectedPuzzle = "...■■x...";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyFirstGapWithAmbiguousSituation2() {
        String puzzleCase = "....■x.";
        String expectedPuzzle = "...■■x.";
        List<Integer> numbersToFind = List.of(2, 2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
    }

    @Test
    void closeWithBeforeEmptyOnlyNotFoundNumber() {
        String puzzleCase = "..x..x...■x...x..x..x";
        String expectedPuzzle = "..x..x.x■■x...x..x..x";
        List<Integer> numbersToFind = List.of(2);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, true), expectedPuzzle);
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
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void productionCase1() {
        String puzzleCase = "■x....■x■.";
        String expectedPuzzle = "■x...x■x■x";
        List<Integer> numbersToFind = List.of(1, 1, 1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 0;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        solver.apply(puzzle, changes);
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
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void case01FromSolveWholeCase013Test() {
        String puzzleCase =     "xx.■.■■xxxx..x.■..■■■...x";
        String expectedPuzzle = "xx.■x■■xxxx..x.■..■■■...x";
        List<Integer> numbersToFind = List.of(2, 2, 9);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void case02FromSolveWholeCase013Test() {
        String puzzleCase =     "xx.■x■■xxxx..x.■..■■■...x";
        String expectedPuzzle = "xx■■x■■xxxx..x.■..■■■...x";
        List<Integer> numbersToFind = List.of(2, 2, 9);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 6;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 7;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(1).found);
    }

}