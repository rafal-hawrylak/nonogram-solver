package org.hawrylak.puzzle.nonogram;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.MergeSubGapForBiggestIfPreviousDoesNotMatch;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.junit.jupiter.api.Test;

import java.util.List;

class MergeSubGapForBiggestIfPreviousDoesNotMatchTest extends PuzzleSolverTestBase {

    private MergeSubGapForBiggestIfPreviousDoesNotMatch solver = new MergeSubGapForBiggestIfPreviousDoesNotMatch(gapFinder, gapFiller, numberSelector);

    @Test
    void ignoreEmpty() {
        String puzzleCase = "......";
        String expectedPuzzle = "......";
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void ignoreFullySolved() {
        String puzzleCase = "x■x■x■";
        String expectedPuzzle = "x■x■x■";
        List<Integer> numbersToFind = List.of(1, 1, 1);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundStart = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(0).foundEnd = 1;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 3;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 5;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 5;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase1() {
        String puzzleCase = ".■■■■.......■.■■■■■................";
        String expectedPuzzle = ".■■■■.......■■■■■■■................";
        List<Integer> numbersToFind = List.of(5, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase2() {
        String puzzleCase = ".■■■■.......■.■■■■■................";
        String expectedPuzzle = ".■■■■.......■.■■■■■................";
        List<Integer> numbersToFind = List.of(5, 13, 1, 5, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase3() {
        String puzzleCase = ".■■■■.......■.■■■■■.............x..";
        String expectedPuzzle = ".■■■■.......■.■■■■■.............x..";
        List<Integer> numbersToFind = List.of(5, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase4() {
        String puzzleCase = ".■■■■.........■■■■■................";
        String expectedPuzzle = ".■■■■.........■■■■■................";
        List<Integer> numbersToFind = List.of(5, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase5() {
        String puzzleCase = ".■■■■..■....■.■■■■■................";
        String expectedPuzzle = ".■■■■..■....■.■■■■■................";
        List<Integer> numbersToFind = List.of(5, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase6() {
        String puzzleCase = ".■■■■.......■.■■■■■................";
        String expectedPuzzle = ".■■■■.......■.■■■■■................";
        List<Integer> numbersToFind = List.of(5, 1, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase7() {
        String puzzleCase = ".■■■■.■.■■■■■................";
        String expectedPuzzle = ".■■■■.■.■■■■■................";
        List<Integer> numbersToFind = List.of(6, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase8() {
        String puzzleCase = ".......■.■■■■■................";
        String expectedPuzzle = ".......■■■■■■■................";
        List<Integer> numbersToFind = List.of(13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase9() {
        String puzzleCase = ".......■.■■■■■................";
        String expectedPuzzle = ".......■.■■■■■................";
        List<Integer> numbersToFind = List.of(5, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase10() {
        String puzzleCase = ".......■.■■■■■................";
        String expectedPuzzle = ".......■.■■■■■................";
        List<Integer> numbersToFind = List.of(1, 13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void solveCase11() {
        String puzzleCase = ".......■.■■■■■..............x.";
        String expectedPuzzle = ".......■.■■■■■..............x.";
        List<Integer> numbersToFind = List.of(13, 1, 2, 2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

}