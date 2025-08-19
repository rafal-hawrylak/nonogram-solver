package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase023Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxxxxxxxxx■xxxxxxx
xxxxxxxxxxxxxxxx■■■xxxxxx
xxxxxxx■xxxxxxx■■■■xxxxxx
xxxxxx■■■xxxxx■■■■■xxxxxx
xxxxxx■■■■■■■x■■■■■xxxxxx
xxxxxx■x■xxx■xx■■■xxxxxxx
xxxxxx■x■xx■■xx■x■xxxxxxx
xxxxxx■x■xxx■x■■x■xxxxxxx
■xxxxx■x■xx■■x■xx■xxxxxxx
■■xxxx■x■xxx■x■x■■xxxxxxx
■■xxxx■■■xx■■■■x■■■■xxxxx
■■■xxx■■■xxx■■■x■■x■■xxxx
■■■xxx■■■xx■■■■x■x■x■■■■x
x■■■xx■■■xxx■■xx■xxx■■x■■
x■■■xx■■■xx■■■x■■■x■■x■x■
xx■■■x■■■xxx■■x■■■xxxxx■■
xx■■■x■■■xx■■■x■■■xx■■■■x
xxx■■■■■■xxx■■x■■xx■■xxxx
xxx■■■■■■xx■■xx■xxx■xxxxx
xxxx■■■■■xxx■x■■xx■■xxxxx
xxx■■■■■■xx■■x■xx■■xxxxxx
xxx■■■■■■■■■■■■■■■■xxxxxx
xxx■xxxxxxxxxxxxxx■xxxxxx
xxx■xxxxxxxxxxxxxx■xxxxxx
xxx■■■■■■■■■■■■■■■■xxxxxx
xxxx■■■■■■■■■■■■■■xxxxxxx
xxxx■xxxxxxxx■■■■■xxxxxxx
xxxx■■■■■■■■■■■■■■xxxxxxx
■■■■■xxxxxxxx■■■■■■■■■■■■
xxxx■■■■■■■■■■■■■■xxxxxxx
xxxxx■xxxxxxx■■■■xxxxxxxx
xxxxx■■■■■■■■■■■■xxxxxxxx
xxxxx■xxxxxx■■■■■xxxxxxxx
xxxxx■■■■■■■■■■■■xxxxxxxx
xxxxxx■■■■■■■■■■xxxxxxxxx""";
        Puzzle before = getTestPuzzle19ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 25;
        var height = 35;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(3));
        rows.add(List.of(1, 4));
        rows.add(List.of(3, 5));
        rows.add(List.of(7, 5));

        rows.add(List.of(1, 1, 1, 3));
        rows.add(List.of(1, 1, 2, 1, 1));
        rows.add(List.of(1, 1, 1, 2, 1));
        rows.add(List.of(1, 1, 1, 2, 1, 1));
        rows.add(List.of(2, 1, 1, 1, 1, 2));

        rows.add(List.of(2, 3, 4, 4));
        rows.add(List.of(3, 3, 3, 2, 2));
        rows.add(List.of(3, 3, 4, 1, 1, 4));
        rows.add(List.of(3, 3, 2, 1, 2, 2));
        rows.add(List.of(3, 3, 3, 3, 2, 1, 1));

        rows.add(List.of(3, 3, 2, 3, 2));
        rows.add(List.of(3, 3, 3, 3, 4));
        rows.add(List.of(6, 2, 2, 2));
        rows.add(List.of(6, 2, 1, 1));
        rows.add(List.of(5, 1, 2, 2));

        rows.add(List.of(6, 2, 1, 2));
        rows.add(List.of(16));
        rows.add(List.of(1, 1));
        rows.add(List.of(1, 1));
        rows.add(List.of(16));

        rows.add(List.of(14));
        rows.add(List.of(1, 5));
        rows.add(List.of(14));
        rows.add(List.of(5, 12));
        rows.add(List.of(14));

        rows.add(List.of(1, 4));
        rows.add(List.of(12));
        rows.add(List.of(1, 5));
        rows.add(List.of(12));
        rows.add(List.of(10));

        cols.add(List.of(5, 1));
        cols.add(List.of(6, 1));
        cols.add(List.of(6, 1));
        cols.add(List.of(6, 5, 1));
        cols.add(List.of(7, 6));

        cols.add(List.of(5, 2, 1, 5));
        cols.add(List.of(19, 2, 1, 1, 1, 2));
        cols.add(List.of(3, 12, 2, 1, 1, 1, 2));
        cols.add(List.of(19, 2, 1, 1, 1, 2));
        cols.add(List.of(1, 1, 2, 1, 1, 1, 2));

        cols.add(List.of(1, 1, 2, 1, 1, 1, 2));
        cols.add(List.of(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2));
        cols.add(List.of(18, 2, 1, 1, 4));
        cols.add(List.of(8, 1, 11));
        cols.add(List.of(2, 6, 3, 11));

        cols.add(List.of(6, 6, 1, 11));
        cols.add(List.of(5, 9, 1, 10));
        cols.add(List.of(12, 3, 2, 6));
        cols.add(List.of(4, 1, 1, 6, 1));
        cols.add(List.of(2, 1, 3, 1));

        cols.add(List.of(4, 2, 1));
        cols.add(List.of(2, 1, 1));
        cols.add(List.of(1, 1, 1, 1));
        cols.add(List.of(2, 2, 1));
        cols.add(List.of(3, 1));
        return new Puzzle(width, height, rows, cols);
    }
}
