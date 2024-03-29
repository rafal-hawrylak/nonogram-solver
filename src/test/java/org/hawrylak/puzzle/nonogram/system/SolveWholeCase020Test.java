package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase020Test extends PuzzleSolverTestBase {

    @Test
//    @Disabled("not solving since fixed FindUnmergableSubGapsForBiggestForFirstAndLastNotFound")
    void test() {
        String expectedPuzzle = """
■■■■■xxxx■■■■xxxxxxxxxxxxxxxxxxxxxx
■■■x■■xx■■x■■■xxxxxxxxxxxxxxxxxxxxx
■■x■x■xx■x■x■■xxxxxxxxxxxxxxxxxxxxx
■xxxx■xx■xxxx■xxxxxxxxxxxxxxxxxxxxx
■■xx■xxxx■xx■xxxxxxxxxxxxxxxxxxxxxx
x■x■xxxxxx■■xxxxx■■■■■■■■■xxxxxxxxx
x■■■xxxxx■■■■xxx■■xxxxxxx■xxxxxxxxx
■■■■■xxxx■■x■xx■■x■x■x■xx■xxxxxxxxx
x■■■■■xxx■■x■x■■xxx■x■xx■■■■■■■■■■x
x■■■x■■■x■■x■x■■xxxxxxx■■xxxxxxx■■x
■x■■xxxxx■■■■■■■■■■■xx■■xxxxx■■■■■x
x■x■xx■■■■■■■■■■■■■■■■■■xx■■■■■■■■x
x■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■xxx■
x■■■■■xxxxx■■■■■■■■■■■■■x■xx■■xx■■x
■■■■■x■x■x■x■■xxxxx■■■■■x■■■■xx■■xx
■■■■■x■x■x■x■xx■■■xx■■■■x■■■■x■■■■x
■■x■■x■x■x■x■x■■■■■xx■■■x■■■■x■■x■x
■xx■x■■■■■■■■■■■■■■■x■■■■■xxxx■■x■x
■xx■xxxxxx■■■■■■■x■■xx■xxxx■■■■■■■x
■■x■■■■■■xxxx■■■xxx■■xxx■■■■xxx■■xx
■■■■■xxx■■■■■■■■xxx■■■■■■xxxxxxxxxx
■■■■■xxxxxxxx■■■■x■■xxxxxxxxxxxxxxx
x■■■xxxxxxxxx■■■■■■■xxxxxxxxxxxxxxx
xxxxxxxxxxxxxx■■■■■xxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxx■■■xxxxxxxxxxxxxxxxx""";
        Puzzle before = getTestPuzzle19ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 35;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5,4));
        rows.add(List.of(3,2,2,3));
        rows.add(List.of(2,1,1,1,1,2));
        rows.add(List.of(1,1,1,1));
        rows.add(List.of(2,1,1,1));

        rows.add(List.of(1,1,2,9));
        rows.add(List.of(3,4,2,1));
        rows.add(List.of(5,2,1,2,1,1,1,1));
        rows.add(List.of(5,2,1,2,1,1,10));
        rows.add(List.of(3,3,2,1,2,2,2));

        rows.add(List.of(1,2,11,2,5));
        rows.add(List.of(1,1,18,8));
        rows.add(List.of(30,1));
        rows.add(List.of(5,13,1,2,2));
        rows.add(List.of(5,1,1,1,2,5,4,2));

        rows.add(List.of(5,1,1,1,1,3,4,4,4));
        rows.add(List.of(2,2,1,1,1,1,5,3,4,2,1));
        rows.add(List.of(1,1,15,5,2,1));
        rows.add(List.of(1,1,7,2,1,7));
        rows.add(List.of(2,6,3,2,4,2));

        rows.add(List.of(5,8,6));
        rows.add(List.of(5,4,2));
        rows.add(List.of(3,7));
        rows.add(List.of(5));
        rows.add(List.of(3));

        cols.add(List.of(5,1,1,8));
        cols.add(List.of(3,6,6,4));
        cols.add(List.of(2,5,4,3));
        cols.add(List.of(1,1,18));
        cols.add(List.of(2,1,2,5,3));

        cols.add(List.of(3,2,2,1,1));
        cols.add(List.of(1,2,4,1));
        cols.add(List.of(1,2,1,1));
        cols.add(List.of(3,2,4,2));
        cols.add(List.of(2,1,7,1,1));

        cols.add(List.of(1,1,8,5,1));
        cols.add(List.of(2,2,4,2,1));
        cols.add(List.of(3,1,13,1));
        cols.add(List.of(3,5,6));
        cols.add(List.of(6,8));

        cols.add(List.of(7,10));
        cols.add(List.of(2,4,4,4));
        cols.add(List.of(2,4,3,3));
        cols.add(List.of(1,1,4,3,3));
        cols.add(List.of(1,1,5,6));

        cols.add(List.of(1,1,5,2));
        cols.add(List.of(1,1,7,1));
        cols.add(List.of(1,1,9,1));
        cols.add(List.of(1,9,1));
        cols.add(List.of(1,2,1,1,2));

        cols.add(List.of(4,6,1));
        cols.add(List.of(1,2,3,1));
        cols.add(List.of(1,2,3,2));
        cols.add(List.of(1,6,1));
        cols.add(List.of(1,4,1));

        cols.add(List.of(1,3,4));
        cols.add(List.of(1,2,6));
        cols.add(List.of(4,3,2));
        cols.add(List.of(4,1,4));
        cols.add(List.of(1));
        return new Puzzle(width, height, rows, cols);
    }
}
