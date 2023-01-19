package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolver;
import org.junit.jupiter.api.Test;

public class SolveWholeCase005Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xx■xxxxxxx
■■■■■■■■xx
xx■xxxx■xx
x■■■xx■■xx
■■■x■■■■■■
x■■xxx■■xx
■■■■■■■x■■
x■■■xx■■xx
x■■xxx■■xx
x■■■xx■■■x""";
        Puzzle puzzle = getTestPuzzle5();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    private Puzzle getTestPuzzle5() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(8));
        rows.add(List.of(1, 1));
        rows.add(List.of(3, 2));
        rows.add(List.of(3, 6));
        rows.add(List.of(2, 2));
        rows.add(List.of(7, 2));
        rows.add(List.of(3, 2));
        rows.add(List.of(2, 2));
        rows.add(List.of(3, 3));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 7));
        cols.add(List.of(10));
        cols.add(List.of(1, 1, 2, 1));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 7));
        cols.add(List.of(5, 3));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 1));
        return new Puzzle(width, height, rows, cols);
    }
}
