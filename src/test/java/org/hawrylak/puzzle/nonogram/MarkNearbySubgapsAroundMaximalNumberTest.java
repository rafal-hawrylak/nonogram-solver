package org.hawrylak.puzzle.nonogram;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.MarkNearbySubgapsAroundMaximalNumber;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.Test;

public class MarkNearbySubgapsAroundMaximalNumberTest extends PuzzleSolverTestBase {

    private MarkNearbySubgapsAroundMaximalNumber solver = new MarkNearbySubgapsAroundMaximalNumber(gapFinder, numberSelector, gapFiller);

    @Test
    void tryToCloseSubGapBeforeMaximalSubgap() {
        String puzzleCase = "■■x■■x...x■x■..■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x...x■x■x.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2,2,1,1,1,1,5,3,4,2,1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseSubGapBeforeMaximalSubgapReversed() {
        String puzzleCase = ".......■■...■...■■■■..■x■x...x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,5,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseSubGapBeforeMaximalSubgap2() {
        String puzzleCase = "■■x■■x...x■x■..■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x...x■x■x.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2,2,1,1,1,1,6,3,4,2,1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseSubGapBeforeMaximalSubgap2Reversed() {
        String puzzleCase = ".......■■...■...■■■■..■x■x...x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,6,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseTwoSubGapsBeforeMaximalSubgap() {
        String puzzleCase = "■■x■■x....■x■..■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x...x■x■x.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2,2,1,1,1,1,5,3,4,2,1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseTwoSubGapsBeforeMaximalSubgapReversed() {
        String puzzleCase = ".......■■...■...■■■■..■x■....x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,5,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void doNotCloseSubGapBeforeMaximalSubgapAsIsMergeable() {
        String puzzleCase = "■■x■■x...x■x.■.■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x...x■x.■.■■■■...■...■■.......";
        List<Integer> numbersToFind = List.of(2,2,1,1,1,1,6,3,4,2,1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void doNotCloseSubGapBeforeMaximalSubgapAsIsMergeableReversed() {
        String puzzleCase = ".......■■...■...■■■■.■.x■x...x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.■.x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,6,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void tryToCloseMaximalSubgap() {
        String puzzleCase = "■■x■■x...x■x■.■■■■■...■...■■.......";
        String expectedPuzzle = "■■x■■x...x■x■x■■■■■x..■...■■.......";
        List<Integer> numbersToFind = List.of(2,2,1,1,1,1,5,3,4,2,1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(6).found);
    }

    @Test
    void tryToCloseMaximalSubgapReversed() {
        String puzzleCase = ".......■■...■...■■■■■.■x■x...x■■x■■";
        String expectedPuzzle = ".......■■...■..x■■■■■x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,5,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 30;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 31;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 33;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 34;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
        assertTrue(puzzle.rowsOrCols.get(0).numbersToFind.get(4).found);
    }

    @Test
    void realCase01() {
        String puzzleCase = "x■■■■■xx■x■.■■xx■■■...........";
        String expectedPuzzle = "x■■■■■xx■x■.■■xx■■■...........";
        List<Integer> numbersToFind = List.of(5,1,4,3,1,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 5;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

}
