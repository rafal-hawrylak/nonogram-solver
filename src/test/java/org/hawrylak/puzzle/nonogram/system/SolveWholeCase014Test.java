package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase014Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxxx■■■■■xxxx
xxxxxxxxxx■■■■■■■xxx
xxxxxxxxxx■■■■■■■xxx
xxxxxxxxx■■■■xx■■xxx
xxxxxxx■■■■■■■■■■xxx
xxxxx■■■x■■■xx■■■■■x
xxxx■■xxx■■xx■■■xx■■
xx■■■xxxx■■■■■xxx■■■
x■■xx■■■■■■■xxxx■■x■
■■xx■■■■■■■■■■x■■xx■
■xx■■■■■■■■■■■■■xxx■
■xx■■■■xx■■■■■■■■x■■
■■x■■■xx■■x■■x■■■■■x
■■■■■■■■■x■■■■x■■x■x
■xx■■■■■x■■■■■x■■x■x
■xxx■■■■■■x■■■■■■x■x
■■xxxxx■■xxx■■x■■x■x
■■■■xxxx■xx■■■xx■x■x
x■x■■■xx■x■■x■xxx■■x
x■xxx■■■■■■xxxxx■■xx
x■xxxxxx■■xxxxx■■xxx
x■■■xxxxx■xxxx■■xxxx
xxx■■■xxx■xxx■■xxxxx
xxxxx■■■x■xx■■xxxxxx
xxxxxxx■■■■■■xxxxxxx""";
        Puzzle before = getTestPuzzle13ProfessionalLevel1();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle13ProfessionalLevel1() {
        var width = 20;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5));
        rows.add(List.of(7));
        rows.add(List.of(7));
        rows.add(List.of(4,2));
        rows.add(List.of(10));

        rows.add(List.of(3,3,5));
        rows.add(List.of(2,2,3,2));
        rows.add(List.of(3,5,3));
        rows.add(List.of(2,7,2,1));
        rows.add(List.of(2,10,2,1));

        rows.add(List.of(1,13,1));
        rows.add(List.of(1,4,8,2));
        rows.add(List.of(2,3,2,2,5));
        rows.add(List.of(9,4,2,1));
        rows.add(List.of(1,5,5,2,1));

        rows.add(List.of(1,6,6,1));
        rows.add(List.of(2,2,2,2,1));
        rows.add(List.of(4,1,3,1,1));
        rows.add(List.of(1,3,1,2,1,2));
        rows.add(List.of(1,6,2));

        rows.add(List.of(1,2,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(6));

        cols.add(List.of(9));
        cols.add(List.of(2,2,6));
        cols.add(List.of(2,1,1,1));
        cols.add(List.of(1,5,2,2));
        cols.add(List.of(2,7,1,1));

        cols.add(List.of(2,8,2,2));
        cols.add(List.of(1,4,3,1,1));
        cols.add(List.of(2,3,4,1,2));
        cols.add(List.of(1,3,2,6,1));
        cols.add(List.of(10,2,6));

        cols.add(List.of(11,2,2,1));
        cols.add(List.of(6,9,2,1));
        cols.add(List.of(5,1,9,2));
        cols.add(List.of(3,1,2,3,6,2));
        cols.add(List.of(3,3,3,1,2));

        cols.add(List.of(7,8,2));
        cols.add(List.of(5,2,7,2));
        cols.add(List.of(1,2,1,2));
        cols.add(List.of(3,8));
        cols.add(List.of(6));
        return new Puzzle(width, height, rows, cols);
    }
}
