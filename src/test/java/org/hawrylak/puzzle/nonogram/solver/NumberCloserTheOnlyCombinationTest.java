package org.hawrylak.puzzle.nonogram.solver;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.Test;

public class NumberCloserTheOnlyCombinationTest extends PuzzleSolverTestBase {

    private CloseTheOnlyCombination solver = new CloseTheOnlyCombination(gapFiller);
    
    @Test
    void closeWithSingleOne() {
        String puzzleCase = ".";
        String expectedPuzzle = "■";
        List<Integer> numbersToFind = List.of(1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleTwo() {
        String puzzleCase = "..";
        String expectedPuzzle = "■■";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleTwoOnes() {
        String puzzleCase = "...";
        String expectedPuzzle = "■x■";
        List<Integer> numbersToFind = List.of(1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleThreeOnes() {
        String puzzleCase = ".....";
        String expectedPuzzle = "■x■x■";
        List<Integer> numbersToFind = List.of(1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleTwoTwos() {
        String puzzleCase = ".....";
        String expectedPuzzle = "■■x■■";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleMixture() {
        String puzzleCase = ".............";
        String expectedPuzzle = "■x■■x■■■x■■■■";
        List<Integer> numbersToFind = List.of(1, 2, 3, 4);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleMixtureReversed() {
        String puzzleCase = ".............";
        String expectedPuzzle = "■■■■x■■■x■■x■";
        List<Integer> numbersToFind = List.of(4, 3, 2, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeWithSingleBigAndSmall() {
        String puzzleCase = ".............";
        String expectedPuzzle = "■■■■■■■■■■■x■";
        List<Integer> numbersToFind = List.of(11, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void doNotCloseWithAlmostSingleAndBig() {
        String puzzleCase = "..............";
        String expectedPuzzle = "..............";
        List<Integer> numbersToFind = List.of(11, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }
}
