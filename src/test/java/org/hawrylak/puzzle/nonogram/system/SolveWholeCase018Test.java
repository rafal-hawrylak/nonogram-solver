package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase018Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxxxxxxx■■■xxxxxxx
xxxxxxxxxxxxx■■■■■■xxxxxx
xxxxxxxxxxx■■■■■■■■■xxxxx
xxxxxxxxxx■■■■■xxxx■■xxxx
xxxxxxxxxx■■■xxxxxxx■■xxx
xxxxxxxxxx■■xxxxx■xx■■xxx
xxxxxxxxxx■■■xxx■■xx■■xxx
xxxxxxxxx■■■■■■x■■xx■xxxx
xxxxxxxx■■■■■xxx■xx■■xxxx
xxxxxxx■■■■xxxxx■xx■xxxxx
xxxxxx■■■xxxxxxxxx■■■xxxx
xxxxxx■■xxxxx■xxxxxx■■xxx
xxxxxx■■xx■xx■■x■xxxx■xxx
xxxxxx■■■■■■x■■x■xxxx■xxx
xxxxx■■■xxxxxx■x■xx■■■■xx
xxxx■■■xxxx■xx■xxxxxxx■■x
xxx■■■■xxxx■xx■xx■xxxxx■■
xxx■■■xxxx■■xxxxx■xxxxxx■
xxx■■■■xxx■■xxxxx■■xxx■x■
xx■■■■■■xx■xxxx■xx■xx■■x■
x■■■xxx■■x■x■xx■x■■x■■x■■
x■■xxxxx■xxx■■x■■■■■■■■■x
■■xxxxxxxxxxx■x■■x■x■■xxx
■■xxxxxxxxxxx■■■xx■■■xxxx
■■■xxxxx■xxx■■x■■x■■xxxxx
■■■■xxxx■■x■■■xx■x■■xxxxx
■■■■■■xxx■■■x■■x■■■xxxxxx
x■■■■■■■■■■■xx■x■■■xxxxxx
xxx■■■■■■■x■■■■■■■xxxxxxx
■xxxxxxxxxxx■■■■■■xxxxxxx
■■■■xxxxxxxxx■■■■■xxxxxxx
■■■■■■■xxxxxx■■■■■xxxxxxx
■■■■■■■■■■■■x■■■■■xxxxxxx
xxxxx■■■■■■■x■■■■■■xxxxxx
xxxxxxxx■■■x■■■■■■■■xxxxx""";
        Puzzle before = getTestPuzzle17ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle17ProfessionalLevel2() {
        var width = 25;
        var height = 35;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(3));
        rows.add(List.of(6));
        rows.add(List.of(9));
        rows.add(List.of(5,2));
        rows.add(List.of(3,2));

        rows.add(List.of(2,1,2));
        rows.add(List.of(3,2,2));
        rows.add(List.of(6,2,1));
        rows.add(List.of(5,1,2));
        rows.add(List.of(4,1,1));

        rows.add(List.of(3,3));
        rows.add(List.of(2,1,2));
        rows.add(List.of(2,1,2,1,1));
        rows.add(List.of(6,2,1,1));
        rows.add(List.of(3,1,1,4));

        rows.add(List.of(3,1,1,2));
        rows.add(List.of(4,1,1,1,2));
        rows.add(List.of(3,2,1,1));
        rows.add(List.of(4,2,2,1,1));
        rows.add(List.of(6,1,1,1,2,1));

        rows.add(List.of(3,2,1,1,1,2,2,2));
        rows.add(List.of(2,1,2,9));
        rows.add(List.of(2,1,2,1,2));
        rows.add(List.of(2,3,3));
        rows.add(List.of(3,1,2,2,2));

        rows.add(List.of(4,2,3,1,2));
        rows.add(List.of(6,3,2,3));
        rows.add(List.of(11,1,3));
        rows.add(List.of(7,7));
        rows.add(List.of(1,6));

        rows.add(List.of(4,5));
        rows.add(List.of(7,5));
        rows.add(List.of(12,5));
        rows.add(List.of(7,6));
        rows.add(List.of(3,8));

        cols.add(List.of(5,4));
        cols.add(List.of(8,3));
        cols.add(List.of(3,4,3));
        cols.add(List.of(5,4,3));
        cols.add(List.of(5,3,2));

        cols.add(List.of(6,3,3));
        cols.add(List.of(7,2,2,3));
        cols.add(List.of(6,2,2,2));
        cols.add(List.of(3,1,2,2,2,3));
        cols.add(List.of(3,1,4,3));

        cols.add(List.of(7,2,4,2,3));
        cols.add(List.of(7,1,4,4,2));
        cols.add(List.of(3,3,2,2,2,1));
        cols.add(List.of(3,1,3,6,7));
        cols.add(List.of(3,1,5,1,9));

        cols.add(List.of(3,6,7));
        cols.add(List.of(3,4,3,2,11));
        cols.add(List.of(3,3,3,2,9));
        cols.add(List.of(2,1,10,2));
        cols.add(List.of(2,3,1,1,3,1));

        cols.add(List.of(6,2,1,4));
        cols.add(List.of(3,4,4));
        cols.add(List.of(2,2,1));
        cols.add(List.of(2,2));
        cols.add(List.of(5));
        return new Puzzle(width, height, rows, cols);
    }
}
