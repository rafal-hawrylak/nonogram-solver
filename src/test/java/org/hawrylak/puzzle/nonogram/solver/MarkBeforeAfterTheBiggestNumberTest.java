package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkBeforeAfterTheBiggestNumberTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new MarkBeforeAfterTheBiggestNumber(gapFinder, numberSelector, gapFiller);
    }

    @Test
    void solveCase1() {
        String puzzleCase = "■■x■■x.....x■x.■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x.....x■x.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2, 2, 1, 1, 1, 1, 5, 3, 4, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
        assertEquals(12, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart);
        assertEquals(12, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd);
    }

    @Test
    void solveCase2() {
        String puzzleCase = "■■x■■x.....x■x...■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x.....x■x...■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2, 2, 1, 1, 1, 1, 5, 3, 4, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
    }

    @Test
    void solveCase3() {
        String puzzleCase = "■■x■■x.....x■x.■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x.....x■x.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2, 2, 1, 1, 1, 1, 5, 3, 4, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart = 12;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd = 12;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
        assertEquals(12, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart);
        assertEquals(12, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd);
    }

    @Test
    @Disabled("not implemented yet")
    void solveCase1Reversed() {
        String puzzleCase = ".......■■...■...■■■■.x■x.....x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x.....x■■x■■";
        List<Integer> numbersToFind = List.of(1 ,2 ,4 ,3 ,5 ,1 ,1 ,1 ,1 ,2 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
        assertEquals(22, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart);
        assertEquals(22, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd);
    }

    @Test
    void solveCase2Reversed() {
        String puzzleCase = ".......■■...■...■■■■...x■x.....x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■...x■x.....x■■x■■";
        List<Integer> numbersToFind = List.of(1 ,2 ,4 ,3 ,5 ,1 ,1 ,1 ,1 ,2 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 32;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 35;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 36;
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
    }

    @Test
    void solveCase3Reversed() {
        String puzzleCase = ".......■■...■...■■■■.x■x.....x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x.....x■■x■■";
        List<Integer> numbersToFind = List.of(1 ,2 ,4 ,3 ,5 ,1 ,1 ,1 ,1 ,2 ,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart = 22;
        puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd = 22;
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(5).found);
        assertEquals(22, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundStart);
        assertEquals(22, puzzle.rowsOrCols.get(0).numbersToFind.get(5).foundEnd);
    }

}