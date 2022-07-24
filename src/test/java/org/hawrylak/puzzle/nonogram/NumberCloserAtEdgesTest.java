package org.hawrylak.puzzle.nonogram;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.junit.jupiter.api.Test;

public class NumberCloserAtEdgesTest extends PuzzleSolverTestBase {

    @Test
    void closeAtEdgeWithOne() {
        String puzzleCase = "....■";
        String expectedPuzzle = "...x■";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwo() {
        String puzzleCase = "....■";
        String expectedPuzzle = "..x■■";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoTwo() {
        String puzzleCase = "....■";
        String expectedPuzzle = "..x■■";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithAllFive() {
        String puzzleCase = "....■";
        String expectedPuzzle = "■■■■■";
        List<Integer> numbersToFind = List.of(5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithOneFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■x...";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■x..";
        List<Integer> numbersToFind = List.of(2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithTwoTwoFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■x..";
        List<Integer> numbersToFind = List.of(2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void closeAtEdgeWithAllFiveFromFront() {
        String puzzleCase = "■....";
        String expectedPuzzle = "■■■■■";
        List<Integer> numbersToFind = List.of(5);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        numberCloser.closeAtEdges(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

}
