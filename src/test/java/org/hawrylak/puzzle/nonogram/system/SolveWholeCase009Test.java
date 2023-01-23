package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.PuzzleSolver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolveWholeCase009Test extends PuzzleSolverTestBase {

    @Test
    @Disabled("failing")
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
        Puzzle puzzle = getTestPuzzle9();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
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
        rows.add(List.of(3,2,3));
        rows.add(List.of(3,2,3));
        rows.add(List.of(8));
        cols.add(List.of(3));
        cols.add(List.of(6));
        cols.add(List.of(8));
        cols.add(List.of(5,1));
        cols.add(List.of(6,2));
        cols.add(List.of(6,3));
        cols.add(List.of(7,1));
        cols.add(List.of(4,2));
        cols.add(List.of(6));
        cols.add(List.of(3));
        return new Puzzle(width, height, rows, cols);
    }
}