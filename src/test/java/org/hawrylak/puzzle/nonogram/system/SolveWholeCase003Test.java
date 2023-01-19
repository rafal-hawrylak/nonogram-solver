package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolver;
import org.junit.jupiter.api.Test;

public class SolveWholeCase003Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
■■■■■■■■■■
■■■xxxx■■■
■■xx■■■x■■
■x■xxxxx■■
xxx■■■■■x■
■x■xxxxxx■
x■x■■■■■■■
xx■xx■■■■■
xxx■■■■■■x
xxxxxx■■xx""";
        Puzzle puzzle = getTestPuzzle3();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
    }
    private Puzzle getTestPuzzle3() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(10));
        rows.add(List.of(3, 3));
        rows.add(List.of(2, 3, 2));
        rows.add(List.of(1, 1, 2));
        rows.add(List.of(5, 1));
        rows.add(List.of(1, 1, 1));
        rows.add(List.of(1, 7));
        rows.add(List.of(1, 5));
        rows.add(List.of(6));
        rows.add(List.of(2));
        cols.add(List.of(4, 1));
        cols.add(List.of(3, 1));
        cols.add(List.of(2, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 3));
        cols.add(List.of(1, 1, 1, 4));
        cols.add(List.of(2, 1, 4));
        cols.add(List.of(4, 3));
        cols.add(List.of(8));
        return new Puzzle(width, height, rows, cols);
    }
}
