package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase008Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
■■■■■■■■■■■■■■■
■■xxxxx■■■xxxx■
■■■■■xxx■■■xxx■
x■■■■xxx■■xxxxx
x■■■■xxxxx■■■xx
■■■■■■x■xx■■■xx
■■■■■■x■xx■■■xx
■■■■■■■■■x■■■x■
■x■■■xx■■x■■■x■
■■■■■x■■■■■■■■■
xx■■■■■■■■■■■■■
■x■■■■■■■x■■■■■
■x■■■■■■■x■x■■■
■■■■■x■■■■■x■■■
xx■x■xxxxx■x■xx""";
        Puzzle before = getTestPuzzle8();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle8() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(15));
        rows.add(List.of(2,3,1));
        rows.add(List.of(5,3,1));
        rows.add(List.of(4,2));
        rows.add(List.of(4,3));
        rows.add(List.of(6,1,3));
        rows.add(List.of(6,1,3));
        rows.add(List.of(9,3,1));
        rows.add(List.of(1,3,2,3,1));
        rows.add(List.of(5,9));
        rows.add(List.of(13));
        rows.add(List.of(1,7,5));
        rows.add(List.of(1,7,1,3));
        rows.add(List.of(5,5,3));
        rows.add(List.of(1,1,1,1));
        cols.add(List.of(3,5,3));
        cols.add(List.of(8,1,1));
        cols.add(List.of(1,13));
        cols.add(List.of(1,12));
        cols.add(List.of(1,13));
        cols.add(List.of(1,3,3));
        cols.add(List.of(1,1,5));
        cols.add(List.of(2,9));
        cols.add(List.of(4,7));
        cols.add(List.of(4,2,1));
        cols.add(List.of(1,1,11));
        cols.add(List.of(1,8));
        cols.add(List.of(1,11));
        cols.add(List.of(1,5));
        cols.add(List.of(3,7));
        return new Puzzle(width, height, rows, cols);
    }
}
