package org.hawrylak.puzzle.nonogram;

import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.TryToNarrowGapsBetweenGapsWithKnownNumbers;
import org.junit.jupiter.api.Test;

public class TryToNarrowGapsBetweenGapsWithKnownNumbersTest extends PuzzleSolverTestBase {

    private TryToNarrowGapsBetweenGapsWithKnownNumbers solver = new TryToNarrowGapsBetweenGapsWithKnownNumbers(gapFinder, gapFiller, numberSelector);

    @Test
    void markSomeFieldsWithOneNumber() {
        String puzzleCase = "...............x■■■x■■■■■■■x..■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■xx.■.xxx";
        List<Integer> numbersToFind = List.of(1,3,7,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft() {
        String puzzleCase = "...............x■■■x■■■■■■■x..■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■xx.■....";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft02() {
        String puzzleCase = "...............x■■■x■■■■■■■x...■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■x...■....";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft03() {
        String puzzleCase = "...............x■■■x■■■■■■■x.■......";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.■......";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft04() {
        String puzzleCase = "...............x■■■x■■■■■■■x..■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.■■..■.";
        List<Integer> numbersToFind = List.of(1,3,7,3,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft05() {
        String puzzleCase = "...............x■■■x■■■■■■■x..■.....";
        String expectedPuzzle = "...............x■■■x■■■■■■■x..■.....";
        List<Integer> numbersToFind = List.of(1,3,7,3,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft06() {
        String puzzleCase = "...............x■■■x■■■■■■■x...■.....";
        String expectedPuzzle = "...............x■■■x■■■■■■■xx..■.....";
        List<Integer> numbersToFind = List.of(1,3,7,3,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft07() {
        String puzzleCase = "...............x■■■x■■■■■■■x...■■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■xxx.■■....";
        List<Integer> numbersToFind = List.of(1,3,7,3,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromLeft07Double() {
        String puzzleCase = "...............x■■■x■■■■■■■x...■■....";
        String expectedPuzzle = "...............x■■■x■■■■■■■xxx.■■..■.";
        List<Integer> numbersToFind = List.of(1,3,7,3,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight() {
        String puzzleCase = "...............x■■■x■■■■■■■x....■..";
        String expectedPuzzle = "...............x■■■x■■■■■■■x....■.x";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight02() {
        String puzzleCase = "...............x■■■x■■■■■■■x....■...";
        String expectedPuzzle = "...............x■■■x■■■■■■■x....■...";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight03() {
        String puzzleCase = "...............x■■■x■■■■■■■x......■.";
        String expectedPuzzle = "...............x■■■x■■■■■■■x......■.";
        List<Integer> numbersToFind = List.of(1,3,7,2,2);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight04() {
        String puzzleCase = "...............x■■■x■■■■■■■x....■..";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.■..■■.";
        List<Integer> numbersToFind = List.of(1,3,7,2,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight05() {
        String puzzleCase = "...............x■■■x■■■■■■■x.....■..";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.....■..";
        List<Integer> numbersToFind = List.of(1,3,7,2,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight06() {
        String puzzleCase = "...............x■■■x■■■■■■■x.....■...";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.....■..x";
        List<Integer> numbersToFind = List.of(1,3,7,2,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight07() {
        String puzzleCase = "...............x■■■x■■■■■■■x....■■...";
        String expectedPuzzle = "...............x■■■x■■■■■■■x....■■.xx";
        List<Integer> numbersToFind = List.of(1,3,7,2,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    @Test
    void markOneFieldWithTwoNumbersFillFromRight07Double() {
        String puzzleCase = "...............x■■■x■■■■■■■x....■■...";
        String expectedPuzzle = "...............x■■■x■■■■■■■x.■..■■.xx";
        List<Integer> numbersToFind = List.of(1,3,7,2,3);
        Puzzle puzzle = puzzleStringConverter.fromString(puzzleCase, numbersToFind, true);
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundStart = 16;
        puzzle.rowsOrCols.get(0).numbersToFind.get(1).foundEnd = 18;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).found = true;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundStart = 20;
        puzzle.rowsOrCols.get(0).numbersToFind.get(2).foundEnd = 26;
        print("before", puzzle);
        var changes = new ChangedInIteration(puzzle);
        solver.apply(puzzle, changes);
        solver.apply(puzzle, changes);
        print("after", puzzle);
        assertPuzzle(puzzle, expectedPuzzle);
    }
}
