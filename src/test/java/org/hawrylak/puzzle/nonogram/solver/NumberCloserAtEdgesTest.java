package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberCloserAtEdgesTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new CloseAtEdges(rowSelector, gapFiller);
    }

    @Test
    void closeAtEdgeWithOne() {
        String puzzleCase = "....■";
        String expectedPuzzle = "...x■";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwo() {
        String puzzleCase = "....■";
        String expectedPuzzle = "..x■■";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoTwo() {
        String puzzleCase = "....■";
        String expectedPuzzle = "..x■■";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithAllFive() {
        String puzzleCase = "....■";
        String expectedPuzzle = "■■■■■";
        List<Integer> numbersToFind = List.of(5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithOneFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■x...";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■x..";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoTwoFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■x..";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithAllFiveFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■■■■";
        List<Integer> numbersToFind = List.of(5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        solveAndAssert(puzzle, expectedPuzzle);
    }

}
