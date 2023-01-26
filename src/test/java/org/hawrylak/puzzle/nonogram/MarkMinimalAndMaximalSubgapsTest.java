package org.hawrylak.puzzle.nonogram;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.MarkMinimalAndMaximalSubgaps;
import org.junit.jupiter.api.Test;

public class MarkMinimalAndMaximalSubgapsTest extends PuzzleSolverTestBase {

    private MarkMinimalAndMaximalSubgaps solver = new MarkMinimalAndMaximalSubgaps(gapFinder, numberSelector, gapFiller);

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
    void tryToCloseSubGapAfterMaximalSubgap() {
        String puzzleCase = ".......■■...■...■■■■..■x■x...x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,5,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }


    @Test
    void tryToCloseTwoSubGapsAfterMaximalSubgap() {
        String puzzleCase = ".......■■...■...■■■■..■x■....x■■x■■";
        String expectedPuzzle = ".......■■...■...■■■■.x■x■x...x■■x■■";
        List<Integer> numbersToFind = List.of(1,2,4,3,5,1,1,1,1,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, false);
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundStart = 0;
        puzzle.rowsOrCols.get(0).numbersToFind.get(9).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(10).foundEnd = 4;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }
}
