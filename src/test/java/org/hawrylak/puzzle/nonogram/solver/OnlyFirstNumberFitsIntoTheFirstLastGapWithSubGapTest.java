package org.hawrylak.puzzle.nonogram.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGapTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new OnlyFirstNumberFitsIntoTheFirstLastGapWithSubGap(gapFinder, numberSelector, gapFiller);
    }

    @Test
    void solveCase1() {
        String puzzleCase = "..■.x..■......................";
        String expectedPuzzle = "xx■xx..■......................";
        List<Integer> numbersToFind = List.of(1, 4, 6, 1, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase2() {
        String puzzleCase = "..■...x..■......................";
        String expectedPuzzle = "..■...x..■......................";
        List<Integer> numbersToFind = List.of(1, 4, 6, 1, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase3() {
        String puzzleCase = ".■....x..■......................";
        String expectedPuzzle = ".■....x..■......................";
        List<Integer> numbersToFind = List.of(1, 4, 6, 1, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase4() {
        String puzzleCase = "..■.x..x......................";
        String expectedPuzzle = "xx■xx..x......................";
        List<Integer> numbersToFind = List.of(1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase5() {
        String puzzleCase = "..■.x..■......................";
        String expectedPuzzle = "x.■.x..■......................";
        List<Integer> numbersToFind = List.of(2, 4, 6, 1, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase6() {
        String puzzleCase = ".....■■■.....x.";
        String expectedPuzzle = ".....■■■.....x.";
        List<Integer> numbersToFind = List.of(8);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }
}