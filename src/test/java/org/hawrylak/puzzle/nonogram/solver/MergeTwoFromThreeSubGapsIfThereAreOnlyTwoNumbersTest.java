package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeTwoFromThreeSubGapsIfThereAreOnlyTwoNumbersTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new MergeTwoFromThreeSubGapsIfThereAreOnlyTwoNumbers(gapFinder, gapFiller, numberSelector);
    }

    @Test
    void fillTheGapExampleFromCase022() {
        String puzzleCase = "...■■■■■■.■.x..■■■■■■■■..";
        String expectedPuzzle = "...■■■■■■■■.x..■■■■■■■■..";
        List<Integer> numbersToFind = List.of(9, 10);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapExampleFromCase022CloseTheFirstNumber() {
        String puzzleCase = "..■■■■■■■.■.x..■■■■■■■■..";
        String expectedPuzzle = ".x■■■■■■■■■xx..■■■■■■■■..";
        List<Integer> numbersToFind = List.of(9, 10);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(0).found);
        assertEquals(2, puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart);
        assertEquals(10, puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(1).found);
    }

    @Test
    void fillTheGapExampleFromCase022WithOneGapFilled() {
        String puzzleCase = "■x...■■■■■■.■.x..■■■■■■■■..";
        String expectedPuzzle = "■x...■■■■■■■■.x..■■■■■■■■..";
        List<Integer> numbersToFind = List.of(1, 9, 10);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 0;
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapExampleFromCase022Reversed() {
        String puzzleCase = "..■■■■■■■■..x.■.■■■■■■...";
        String expectedPuzzle = "..■■■■■■■■..x.■■■■■■■■...";
        List<Integer> numbersToFind = List.of(10, 9);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapExampleFromCase022CloseTheFirstNumberReversed() {
        String puzzleCase = "..■■■■■■■■..x.■.■■■■■■■..";
        String expectedPuzzle = "..■■■■■■■■..xx■■■■■■■■■x.";
        List<Integer> numbersToFind = List.of(10, 9);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        assertFalse(puzzle.rowsOrCols.get(0).numbersToFind.get(0).found);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(1).found);
        assertEquals(14, puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart);
        assertEquals(22, puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd);
    }

    @Test
    void fillTheGapExampleFromCase022WithOneGapFilledReversed() {
        String puzzleCase = "..■■■■■■■■..x.■.■■■■■■...x■";
        String expectedPuzzle = "..■■■■■■■■..x.■■■■■■■■...x■";
        List<Integer> numbersToFind = List.of(10, 9, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 26;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        solveAndAssert(puzzle, expectedPuzzle);
    }

}