package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.TryToAssignNumberToFilledGap;
import org.junit.jupiter.api.Test;

public class GapFillerTryToAssignNumberToFilledGapTest extends PuzzleSolverTestBase {

    private TryToAssignNumberToFilledGap solver = new TryToAssignNumberToFilledGap(gapFinder, numberSelector, gapFiller);

    @Test
    void tryToAssignNumberToFilledGap() {
        String puzzleCase = "..■■...■■■■x■■■■x.■■..■..";
        String expectedPuzzle = "..■■...■■■■x■■■■x.■■..■..";
        List<Integer> numbersToFind = List.of(5, 4, 4, 4, 3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }

    @Test
    void tryToAssignNumberToFilledGapShouldNotMark() {
        String puzzleCase = ".....x■x.....";
        String expectedPuzzle = ".....x■x.....";
        List<Integer> numbersToFind = List.of(1, 1, 1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        puzzle.rowsOrCols.get(0).numbersToFind.forEach(n -> assertFalse(n.found));
    }

    @Test
    void tryToAssignNumberToFilledGapShouldNotMark2() {
        String puzzleCase = ".......x■■■xxx■.■......■■..";
        String expectedPuzzle = ".......x■■■xxx■.■......■■..";
        List<Integer> numbersToFind = List.of(3, 3, 1, 1, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        puzzle.rowsOrCols.get(0).numbersToFind.forEach(n -> assertFalse(n.found));
    }

    @Test
    void tryToAssignNumberToFilledGap2() {
        String puzzleCase =     "..■..■■.x■■xxx■■.....■■..";
        String expectedPuzzle = "..■..■■.x■■xxx■■.....■■..";
        List<Integer> numbersToFind = List.of(3, 2, 2, 2, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(2).found);
    }
}
