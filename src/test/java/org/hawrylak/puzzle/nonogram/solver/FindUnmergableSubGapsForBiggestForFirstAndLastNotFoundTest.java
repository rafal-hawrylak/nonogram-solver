package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindUnmergableSubGapsForBiggestForFirstAndLastNotFoundTest extends PuzzleSolverTestBase {

    @BeforeEach
    void before() {
        solver = new FindUnmergableSubGapsForBiggestForFirstAndLastNotFound(gapFinder, gapCloser, numberSelector);
    }

    @Test
    void shouldNotCloseFieldWhenTooFarFromEndEdge2() {
        String puzzleCase = ".............................■.■...";
        String expectedPuzzle = ".............................■.■...";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void shouldCloseFieldWhenExactlyAtEndEdge() {
        String puzzleCase = "..............................■.■..";
        String expectedPuzzle = "..............................■x■..";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void shouldCloseFieldWhenGoodDistanceFromEndEdge() {
        String puzzleCase = "...............................■.■.";
        String expectedPuzzle = "...............................■x■.";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void shouldNotCloseFieldWhenTooFarFromBeginEdge2() {
        String puzzleCase = "...■.■.............................";
        String expectedPuzzle = "...■.■.............................";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void shouldCloseFieldWhenExactlyAtBeginEdge() {
        String puzzleCase = "..■.■..............................";
        String expectedPuzzle = "..■x■..............................";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }

    @Test
    void shouldCloseFieldWhenGoodDistanceFromBeginEdge() {
        String puzzleCase = ".■.■...............................";
        String expectedPuzzle = ".■x■...............................";
        List<Integer> numbersToFind = List.of(2,6,3,2,4,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        solveAndAssert(puzzle, expectedPuzzle);
    }
}
