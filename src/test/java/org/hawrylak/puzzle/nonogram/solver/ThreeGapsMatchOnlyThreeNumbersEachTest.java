package org.hawrylak.puzzle.nonogram.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ThreeGapsMatchOnlyThreeNumbersEachTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new ThreeGapsMatchOnlyThreeNumbersEach(gapFinder, gapFiller);
    }

    @Test
    void solveCase1() {
        String puzzleCase = "........xx..■■■■■■■■..x.......";
        String expectedPuzzle = ".■■■■■■.xxx.■■■■■■■■.xx..■■■..";
        List<Integer> numbersToFind = List.of(7, 9, 5);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase2() {
        String puzzleCase = "........xx..■■■■■■■■.........";
        String expectedPuzzle = "........xx..■■■■■■■■.........";
        List<Integer> numbersToFind = List.of(7, 9, 5);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase3() {
        String puzzleCase = "............■■■■■■■■.........";
        String expectedPuzzle = "............■■■■■■■■.........";
        List<Integer> numbersToFind = List.of(7, 9, 5);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase4() {
        String puzzleCase = "........xx............x.......";
        String expectedPuzzle = "........xx............x.......";
        List<Integer> numbersToFind = List.of(7, 5, 5);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase5() {
        String puzzleCase = "........xx..■■■■■■■■..x.......";
        String expectedPuzzle = "........xx..■■■■■■■■..x.......";
        List<Integer> numbersToFind = List.of(5, 9, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

}