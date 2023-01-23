package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.PuzzleSolver;
import org.junit.jupiter.api.Test;

public class SolveWholeCase010Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxx■xxxx
xx■■xxxxx■■■xxx
x■■■■xxx■■■■■xx
x■■■■xx■■■■■■■x
xx■■xx■■xx■■■■■
xxxxx■■xx■■■■■■
xxxx■■xx■■■■■■■
xxx■■xx■■■■■■■■
xx■■xx■■■■■■■■■
x■■xx■■■■■■■■■■
■■■■■■■■■■■■■■■
■■■■■■■■■■■■■x■
■■■■■■■■x■■■x■■
■■■■xxxx■■■xxx■
xx■■■■xxxxxxxxx""";
        Puzzle puzzle = getTestPuzzle10();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    private Puzzle getTestPuzzle10() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(2,3));
        rows.add(List.of(4,5));
        rows.add(List.of(4,7));
        rows.add(List.of(2,2,5));
        rows.add(List.of(2,6));
        rows.add(List.of(2,7));
        rows.add(List.of(2,8));
        rows.add(List.of(2,9));
        rows.add(List.of(2,10));
        rows.add(List.of(15));
        rows.add(List.of(13,1));
        rows.add(List.of(8,3,2));
        rows.add(List.of(4,3,1));
        rows.add(List.of(4));
        cols.add(List.of(4));
        cols.add(List.of(2,5));
        cols.add(List.of(4,7));
        cols.add(List.of(4,2,5));
        cols.add(List.of(2,2,3,1));
        cols.add(List.of(2,4,1));
        cols.add(List.of(2,5));
        cols.add(List.of(2,6));
        cols.add(List.of(2,6,1));
        cols.add(List.of(3,9));
        cols.add(List.of(14));
        cols.add(List.of(12));
        cols.add(List.of(10));
        cols.add(List.of(8,1));
        cols.add(List.of(10));
        return new Puzzle(width, height, rows, cols);
    }
}
