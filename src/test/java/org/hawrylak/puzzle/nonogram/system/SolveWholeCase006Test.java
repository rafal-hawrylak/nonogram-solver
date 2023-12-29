package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase006Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxx■■■■■■■xxxx
xxxxx■■■■■xxxxx
xx■■■■■■■■■■■xx
■x■xx■■■■■xx■xx
■■■■■■■■■■■■■xx
■■xx■■■■■■■xxxx
■■x■■■■■■■■■■xx
■x■■■■■■■■■■■x■
■x■■■■■■■■■■■x■
■■■■■■■■■■■■■■■
xx■■■■■■■■■■■■x
xx■■■■■■■■■■■■x
x■■■■■■■■■■■■■■
x■■■■■■■x■■■■■■
■■■■■■■■xxxxxxx""";
        Puzzle before = getTestPuzzle6();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle6() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(7));
        rows.add(List.of(5));
        rows.add(List.of(11));
        rows.add(List.of(1, 1, 5, 1));
        rows.add(List.of(13));
        rows.add(List.of(2, 7));
        rows.add(List.of(2, 10));
        rows.add(List.of(1, 11, 1));
        rows.add(List.of(1, 11, 1));
        rows.add(List.of(15));
        rows.add(List.of(12));
        rows.add(List.of(12));
        rows.add(List.of(14));
        rows.add(List.of(7, 6));
        rows.add(List.of(8));
        cols.add(List.of(7, 1));
        cols.add(List.of(3, 1, 3));
        cols.add(List.of(3, 8));
        cols.add(List.of(1, 1, 9));
        cols.add(List.of(1, 1, 11));
        cols.add(List.of(15));
        cols.add(List.of(15));
        cols.add(List.of(15));
        cols.add(List.of(13));
        cols.add(List.of(14));
        cols.add(List.of(1, 1, 10));
        cols.add(List.of(1, 1, 8));
        cols.add(List.of(3, 8));
        cols.add(List.of(5));
        cols.add(List.of(3, 2));
        return new Puzzle(width, height, rows, cols);
    }
}
