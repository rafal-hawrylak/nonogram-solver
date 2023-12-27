package org.hawrylak.puzzle.nonogram.solver;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.Test;

import java.util.List;

class FillTheNumbersWithStartAndEndNotConnectedTest extends PuzzleSolverTestBase {

    private FillTheNumbersWithStartAndEndNotConnected solver = new FillTheNumbersWithStartAndEndNotConnected(gapFinder, numberSelector, gapFiller);

    @Test
    void fillTheGapForTheFirstNumber() {
        String puzzleCase = "..■..■■..■■■■■.";
        String expectedPuzzle = "..■■■■■..■■■■■.";
        List<Integer> numbersToFind = List.of(5, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapForTheLastNumber() {
        String puzzleCase = ".■■■■■..■■..■..";
        String expectedPuzzle = ".■■■■■..■■■■■..";
        List<Integer> numbersToFind = List.of(5, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapForTheFirstNumberWhichIsDoubled() {
        String puzzleCase = "....■.■.■■■■■..";
        String expectedPuzzle = "....■.■.■■■■■..";
        List<Integer> numbersToFind = List.of(3, 3, 5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void fillTheGapForTheLastNumberWhichIsDoubled() {
        String puzzleCase = "..■■■■■.■.■....";
        String expectedPuzzle = "..■■■■■.■.■....";
        List<Integer> numbersToFind = List.of(5, 3, 3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

}