package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GapFillerTryToAssignNumberToFilledGapTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new TryToAssignNumberToFilledGap(gapFinder, numberSelector, gapFiller);
    }

    @Test
    void tryToAssignNumberToFilledGap() {
        String puzzleCase = "..■■...■■■■x■■■■x.■■..■..";
        String expectedPuzzle = "..■■...■■■■x■■■■x.■■..■..";
        List<Integer> numbersToFind = List.of(5, 4, 4, 4, 3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }

    @Test
    void tryToAssignNumberToFilledGapShouldNotMark() {
        String puzzleCase = ".....x■x.....";
        String expectedPuzzle = ".....x■x.....";
        List<Integer> numbersToFind = List.of(1, 1, 1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        puzzle.rowsOrCols.get(0).numbersToFind.forEach(n -> assertFalse(n.found));
    }

    @Test
    void tryToAssignNumberToFilledGapShouldNotMark2() {
        String puzzleCase = ".......x■■■xxx■.■......■■..";
        String expectedPuzzle = ".......x■■■xxx■.■......■■..";
        List<Integer> numbersToFind = List.of(3, 3, 1, 1, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        puzzle.rowsOrCols.get(0).numbersToFind.forEach(n -> assertFalse(n.found));
    }

    @Test
    void tryToAssignNumberToFilledGap2() {
        String puzzleCase =     "..■..■■.x■■xxx■■.....■■..";
        String expectedPuzzle = "..■..■■.x■■xxx■■.....■■..";
        List<Integer> numbersToFind = List.of(3, 2, 2, 2, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }
}
