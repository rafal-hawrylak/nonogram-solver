package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FitTheOnlyCombinationBeforeFirstOrAfterLastClosedTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new FitTheOnlyCombinationBeforeFirstOrAfterLastClosed(gapFinder, gapFiller, numberSelector);
    }

    @Test
    void solveCase1() {
        String puzzleCase = "...■x.x■■x...■..x...";
        String expectedPuzzle = "...■x.x■■x■■x■■.x.■.";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 7;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 8;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase2() {
        String puzzleCase = "...■x.x■■x...■..x....";
        String expectedPuzzle = "...■x.x■■x■■x■■.x....";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 7;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 8;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase3() {
        String puzzleCase = "...■x.x■■x......x...";
        String expectedPuzzle = "...■x.x■■x.■..■.x.■.";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 7;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 8;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase4() {
        String puzzleCase = "...■x.x■■x...■..x...";
        String expectedPuzzle = "...■x.x■■x...■..x...";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase5() {
        String puzzleCase = "...■x.x■■x...■....x...";
        String expectedPuzzle = "...■x.x■■x...■....x...";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 7;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 8;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }

    @Test
    void solveCase6() {
        String puzzleCase = "...■x.x■■x...■..x...x..";
        String expectedPuzzle = "...■x.x■■x...■..x...x..";
        List<Integer> numbersToFind = List.of(2, 1, 2, 2, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 7;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 8;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }

    @Test
    void solveCase1Reversed() {
        String puzzleCase = "...x..■...x■■x.x■...";
        String expectedPuzzle = ".■.x.■■.■.x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundStart = 11;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundEnd = 12;
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase2Reversed() {
        String puzzleCase = "....x..■...x■■x.x■...";
        String expectedPuzzle = "....x.■■.■.x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundStart = 12;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundEnd = 13;
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase3Reversed() {
        String puzzleCase = "...x......x■■x.x■...";
        String expectedPuzzle = ".■.x.■..■.x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundStart = 11;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundEnd = 12;
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase4Reversed() {
        String puzzleCase = "...x..■...x■■x.x■...";
        String expectedPuzzle = "...x..■...x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase5Reversed() {
        String puzzleCase = "...x....■...x■■x.x■...";
        String expectedPuzzle = "...x....■...x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundStart = 13;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundEnd = 14;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

    @Test
    void solveCase6Reversed() {
        String puzzleCase = "..x...x..■...x■■x.x■...";
        String expectedPuzzle = "..x...x..■...x■■x.x■...";
        List<Integer> numbersToFind = List.of(2 ,2 ,2 ,2 ,1 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundStart = 14;
        puzzle.rowsOrCols.get(0).numbersToFind.get(3).foundEnd = 15;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(3).found);
    }

}