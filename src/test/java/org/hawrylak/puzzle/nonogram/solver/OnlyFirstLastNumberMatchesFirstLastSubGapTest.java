package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OnlyFirstLastNumberMatchesFirstLastSubGapTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new OnlyFirstLastNumberMatchesFirstLastSubGap(gapFinder, numberSelector, gapFiller, gapCloser);
    }

    @Test
    void solveCase1() {
        String puzzleCase = ".....................■■■■...........";
        String expectedPuzzle = ".....................■■■■...xxxxxxxx";
        List<Integer> numbersToFind = List.of(4, 1, 1, 7);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase2() {
        String puzzleCase = ".....................■■■■............";
        String expectedPuzzle = ".....................■■■■............";
        List<Integer> numbersToFind = List.of(4, 1, 1, 7);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase3() {
        String puzzleCase = ".....................■■■■.........xx";
        String expectedPuzzle = ".....................■■■■...xxxxxxxx";
        List<Integer> numbersToFind = List.of(4, 1, 1, 7);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase4() {
        String puzzleCase = "...x.................■■■■...........";
        String expectedPuzzle = "...x.................■■■■...xxxxxxxx";
        List<Integer> numbersToFind = List.of(4, 1, 1, 7);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase5() {
        String puzzleCase = ".....................■■■■...........";
        String expectedPuzzle = ".....................■■■■...........";
        List<Integer> numbersToFind = List.of(4, 1, 1, 6);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase1Reversed() {
        String puzzleCase = "...........■■■■.....................";
        String expectedPuzzle = "xxxxxxxx...■■■■.....................";
        List<Integer> numbersToFind = List.of(7 ,1 ,1 ,4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase2Reversed() {
        String puzzleCase = "............■■■■.....................";
        String expectedPuzzle = "............■■■■.....................";
        List<Integer> numbersToFind = List.of(7 ,1 ,1 ,4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase3Reversed() {
        String puzzleCase = "xx.........■■■■.....................";
        String expectedPuzzle = "xxxxxxxx...■■■■.....................";
        List<Integer> numbersToFind = List.of(7 ,1 ,1 ,4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase4Reversed() {
        String puzzleCase = "...........■■■■.................x...";
        String expectedPuzzle = "xxxxxxxx...■■■■.................x...";
        List<Integer> numbersToFind = List.of(7 ,1 ,1 ,4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase5Reversed() {
        String puzzleCase = "...........■■■■.....................";
        String expectedPuzzle = "...........■■■■.....................";
        List<Integer> numbersToFind = List.of(6 ,1 ,1 ,4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

}