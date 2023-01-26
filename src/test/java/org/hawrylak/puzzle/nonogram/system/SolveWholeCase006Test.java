package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.junit.jupiter.api.Test;

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
        Puzzle puzzle = getTestPuzzle6();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);

        System.out.println(puzzle.compact());
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
