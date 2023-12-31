package org.hawrylak.puzzle.nonogram.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TwoOutOfMoreGapsMatchOnlyTwoFirstNumbersEachTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new TwoOutOfMoreGapsMatchOnlyTwoFirstNumbersEach(gapFinder, gapFiller, gapCloser);
    }

    @Test
    void solveCase1() {
        String puzzleCase = "......x.xx..■■■■■■■■..x..x....";
        String expectedPuzzle = ".■■■■.xxxx..■■■■■■■■..x..x....";
        List<Integer> numbersToFind = List.of(5, 9, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase2() {
        String puzzleCase = "......xxxx..■■■■■■■■..x..x....";
        String expectedPuzzle = "......xxxx..■■■■■■■■..x..x....";
        List<Integer> numbersToFind = List.of(5, 9, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase3() {
        String puzzleCase = "......x.xx.......■■■■■■■■..x..x....";
        String expectedPuzzle = "......x.xx.......■■■■■■■■..x..x....";
        List<Integer> numbersToFind = List.of(5, 9, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

    @Test
    void solveCase4() {
        String puzzleCase = "......x.xx............x..x....";
        String expectedPuzzle = ".■■■■.xxxx............x..x....";
        List<Integer> numbersToFind = List.of(5, 9, 1);
        solveAndAssert(puzzleStringConverter.fromString(puzzleCase, numbersToFind, false), expectedPuzzle);
    }

}