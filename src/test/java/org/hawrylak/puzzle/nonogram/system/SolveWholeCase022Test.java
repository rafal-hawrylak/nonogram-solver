package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase022Test extends PuzzleSolverTestBase {

    // solved by Guesser
    @Test
    /*
      iteration = 38
      completion 29% (218 / 750)
     */
    void test() {
        String expectedPuzzle = """
xxxxxxx■■■■■■■xxxxxxx■■■x
xxxxxx■■■xxxx■■xxxxx■■x■■
xxxxx■■■xxxxxx■■xxx■■xxx■
xxxxx■■xx■■■■xx■■xx■xxxx■
xxxx■■xx■■xx■■xx■xx■xxxxx
xxxx■xx■■xxxx■■x■■x■x■■■x
xxxx■xx■■xxxxx■xx■x■x■x■■
xxxx■xx■xxxxxx■x■■■■x■xx■
xxx■■x■■xx■■■x■■x■x■x■xxx
xxx■xx■xx■■x■■■■x■x■■■xxx
xxx■xx■x■■xxxx■■xxx■■xxxx
xxx■xx■x■xxxx■■■xx■■■xxxx
xxx■xx■xxxx■■■■■xx■■■■■xx
xxxxxx■xx■■■■■■■xx■■■■■■■
xxxxxx■xx■■■■■■xxxx■■■■■■
xxxxxx■xx■■■■■■■■■■■■■■■■
xxxxxxxxxx■■■■■■■■■■■■■■x
xxxxx■■■■■■■■■■■■■■■■■■xx
xx■■■■■■■■xx■■■■■■■■x■xxx
■■■■■■x■■xxx■■xxxxxxx■x■■
■■■xx■■■xx■x■■xxxxxxx■xxx
■xx■■xxx■■xx■■■■■■■x■■x■x
■■■xxx■■■x■xx■■xxxxx■xxx■
■xxx■■■xx■■x■■■■■■■x■■x■x
xxx■■xxx■■xx■■■xxxxxx■xx■
xx■■xx■■■x■■■■■■■■■■■x■■x
x■■x■■■xx■■■■xxxxxxxxxx■■
■xx■■xx■■■■■■■■■■■■■■■■■x
■xxxxx■■■xxxxxxxxxxxxxxxx
xxx■■■■■■■■■xxx■■■■■■■■■■""";
        Puzzle before = getTestPuzzle19ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 25;
        var height = 30;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(7, 3));
        rows.add(List.of(3, 2, 2, 2));
        rows.add(List.of(3, 2, 2, 1));
        rows.add(List.of(2, 4, 2, 1, 1));
        rows.add(List.of(2, 2, 2, 1, 1));

        rows.add(List.of(1, 2, 2, 2, 1, 3));
        rows.add(List.of(1, 2, 1, 1, 1, 1, 2));
        rows.add(List.of(1, 1, 1, 4, 1, 1));
        rows.add(List.of(2, 2, 3, 2, 1, 1, 1));
        rows.add(List.of(1, 1, 2, 4, 1, 3));

        rows.add(List.of(1, 1, 2, 2, 2));
        rows.add(List.of(1, 1, 1, 3, 3));
        rows.add(List.of(1, 1, 5, 5));
        rows.add(List.of(1, 7, 7));
        rows.add(List.of(1, 6, 6));

        rows.add(List.of(1, 16));
        rows.add(List.of(14));
        rows.add(List.of(18));
        rows.add(List.of(8, 8, 1));
        rows.add(List.of(6, 2, 2, 1, 2));

        rows.add(List.of(3, 3, 1, 2, 1));
        rows.add(List.of(1, 2, 2, 7, 2, 1));
        rows.add(List.of(3, 3, 1, 2, 1, 1));
        rows.add(List.of(1, 3, 2, 7, 2, 1));
        rows.add(List.of(2, 2, 3, 1, 1));

        rows.add(List.of(2, 3, 11, 2));
        rows.add(List.of(2, 3, 4, 2));
        rows.add(List.of(1, 2, 17));
        rows.add(List.of(1, 3));
        rows.add(List.of(9, 10));

        cols.add(List.of(5, 2));
        cols.add(List.of(2, 1, 1));
        cols.add(List.of(3, 1, 2));
        cols.add(List.of(5, 2, 1, 2, 1, 1));
        cols.add(List.of(5, 2, 1, 2, 2, 1));

        cols.add(List.of(3, 4, 1, 1, 1));
        cols.add(List.of(3, 8, 2, 1, 2, 2, 2));
        cols.add(List.of(3, 4, 4, 1, 1, 3));
        cols.add(List.of(2, 3, 2, 3, 2, 2, 3));
        cols.add(List.of(1, 2, 2, 3, 2, 1, 2, 2, 1));

        cols.add(List.of(1, 1, 2, 5, 1, 2, 3, 1));
        cols.add(List.of(1, 1, 1, 6, 3, 1));
        cols.add(List.of(1, 2, 2, 10, 5));
        cols.add(List.of(2, 2, 1, 15, 1));
        cols.add(List.of(2, 14, 5, 1));

        cols.add(List.of(2, 6, 4, 1, 1, 1, 1, 1));
        cols.add(List.of(3, 1, 4, 1, 1, 1, 1, 1));
        cols.add(List.of(5, 4, 1, 1, 1, 1, 1));
        cols.add(List.of(1, 3, 4, 1, 1, 1, 1, 1));
        cols.add(List.of(17, 1, 1, 1));

        cols.add(List.of(2, 9, 3, 1, 1, 1));
        cols.add(List.of(2, 5, 10, 2, 1, 1));
        cols.add(List.of(1, 1, 6, 1, 1, 1));
        cols.add(List.of(2, 2, 4, 1, 1, 1, 3, 1));
        cols.add(List.of(3, 2, 3, 1, 1, 1, 1, 1));
        return new Puzzle(width, height, rows, cols);
    }
}
