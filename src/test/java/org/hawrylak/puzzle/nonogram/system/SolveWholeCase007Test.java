package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase007Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxx■■■■■■x■■■xx
xx■■■xx■■■■■■xx
x■■■■■x■■■■■■■x
x■■■■■■■■■■■■■x
xxxx■■■■■■■■■■x
■■■■■■■■■■■■■■x
x■■■■■■■■■■■■■x
xxxxxxxxx■■■■■■
xxxxxxx■■■■■■■■
xxxxxxxxx■■■■■■
■xxxxxxx■■■■■■■
■■■xxxx■■■■■■■■
x■■xx■■■■■■■■x■
x■■■■■■■■■■xxxx
■■■■■■■■xxxxxxx""";
        Puzzle before = getTestPuzzle7();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle7() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(6,3));
        rows.add(List.of(3,6));
        rows.add(List.of(5,7));
        rows.add(List.of(13));
        rows.add(List.of(10));
        rows.add(List.of(14));
        rows.add(List.of(13));
        rows.add(List.of(6));
        rows.add(List.of(8));
        rows.add(List.of(6));
        rows.add(List.of(1,7));
        rows.add(List.of(3,8));
        rows.add(List.of(2,8,1));
        rows.add(List.of(10));
        rows.add(List.of(8));
        cols.add(List.of(1,2,1));
        cols.add(List.of(2,2,4));
        cols.add(List.of(3,2,4));
        cols.add(List.of(4,2,2));
        cols.add(List.of(7,2));
        cols.add(List.of(1,5,3));
        cols.add(List.of(1,4,3));
        cols.add(List.of(7,1,4));
        cols.add(List.of(7,1,4));
        cols.add(List.of(13));
        cols.add(List.of(14));
        cols.add(List.of(13));
        cols.add(List.of(13));
        cols.add(List.of(10));
        cols.add(List.of(6));
        return new Puzzle(width, height, rows, cols);
    }
}
