package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumberTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new TheBiggestFirstOrLastSubGapFitsOnlyTheBiggestFirstOrLastNumber(gapFinder, numberSelector, gapFiller);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap() {
        String puzzleCase = "............■■■....................";
        String expectedPuzzle = "xxxx........■■■....................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap02() {
        String puzzleCase = "...........■■■.....................";
        String expectedPuzzle = "xxx........■■■.....................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap03() {
        String puzzleCase = "..........■■■......................";
        String expectedPuzzle = "xx........■■■......................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap04() {
        String puzzleCase = "..........■■■......................";
        String expectedPuzzle = "xx........■■■......................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap05() {
        String puzzleCase = ".........■■■.......................";
        String expectedPuzzle = "x........■■■.......................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGap06() {
        String puzzleCase = "........■■■........................";
        String expectedPuzzle = "........■■■........................";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapNotTheFirstOnTheList() {
        String puzzleCase = "............■■■....................";
        String expectedPuzzle = "............■■■....................";
        List<Integer> numbersToFind = List.of(1,11,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTwoMatchingSubGap() {
        String puzzleCase = "............■■■....................";
        String expectedPuzzle = "xxxx........■■■....................";
        List<Integer> numbersToFind = List.of(11,1,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapWithTwoGaps() {
        String puzzleCase = "............■■■.............x......";
        String expectedPuzzle = "xxxx........■■■.............x......";
        List<Integer> numbersToFind = List.of(11,1,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTwoMatchingSubGapWithTwoGaps() {
        String puzzleCase = "............■■■.............x......";
        String expectedPuzzle = "xxxx........■■■.............x......";
        List<Integer> numbersToFind = List.of(11,1,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapReversed() {
        String puzzleCase = "....................■■■............";
        String expectedPuzzle = "....................■■■........xxxx";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapReversed02() {
        String puzzleCase = ".....................■■■...........";
        String expectedPuzzle = ".....................■■■........xxx";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapReversed03() {
        String puzzleCase = "......................■■■..........";
        String expectedPuzzle = "......................■■■........xx";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapReversed04() {
        String puzzleCase = ".......................■■■.........";
        String expectedPuzzle = ".......................■■■........x";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapReversed05() {
        String puzzleCase = "........................■■■........";
        String expectedPuzzle = "........................■■■........";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTheOnlyMatchingSubGapNotTheLastOnTheListReversed() {
        String puzzleCase = "....................■■■............";
        String expectedPuzzle = "....................■■■............";
        List<Integer> numbersToFind = List.of(1,11,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTwoMatchingSubGapReversed() {
        String puzzleCase = "....................■■■............";
        String expectedPuzzle = "....................■■■........xxxx";
        List<Integer> numbersToFind = List.of(3,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }


    @Test
    void caseWithTheOnlyMatchingSubGapWithTwoGapsReversed() {
        String puzzleCase = "......x.............■■■............";
        String expectedPuzzle = "......x.............■■■........xxxx";
        List<Integer> numbersToFind = List.of(2,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void caseWithTwoMatchingSubGapWithTwoGapsReversed() {
        String puzzleCase = "......x.............■■■............";
        String expectedPuzzle = "......x.............■■■........xxxx";
        List<Integer> numbersToFind = List.of(3,1,11);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void realCase01() {
        String puzzleCase = "..................■...........";
        String expectedPuzzle = "..................■...........";
        List<Integer> numbersToFind = List.of(5,6);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void realCase02() {
        String puzzleCase = "...................■..........";
        String expectedPuzzle = "...................■..........";
        List<Integer> numbersToFind = List.of(5,6);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void realCase03() {
        String puzzleCase = "....................■.........";
        String expectedPuzzle = "....................■.........";
        List<Integer> numbersToFind = List.of(5,6);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void realCase04() {
        String puzzleCase = ".....................■........";
        String expectedPuzzle = ".....................■........";
        List<Integer> numbersToFind = List.of(5,6);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }
}
