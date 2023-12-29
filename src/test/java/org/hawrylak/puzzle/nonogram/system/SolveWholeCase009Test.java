package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase009Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxx■■xxxx
xxx■■■■xxx
xx■■■■■■xx
xx■■■■■■xx
x■■■■■■■■x
x■■■■■■■■x
■■■xxx■x■■
■■■x■■■■■■
■■■x■■x■■■
x■■■■■■■■x""";
        Puzzle before = getTestPuzzle9();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle9() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2));
        rows.add(List.of(4));
        rows.add(List.of(6));
        rows.add(List.of(6));
        rows.add(List.of(8));
        rows.add(List.of(8));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,6));
        rows.add(List.of(3,2,3));
        rows.add(List.of(8));
        cols.add(List.of(3));
        cols.add(List.of(6));
        cols.add(List.of(8));
        cols.add(List.of(5,1));
        cols.add(List.of(6,3));
        cols.add(List.of(6,3));
        cols.add(List.of(7,1));
        cols.add(List.of(4,3));
        cols.add(List.of(6));
        cols.add(List.of(3));
        return new Puzzle(width, height, rows, cols);
    }
}
