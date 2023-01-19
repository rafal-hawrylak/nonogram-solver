package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolver;
import org.junit.jupiter.api.Test;

public class SolveWholeCase016Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxx■■x■■xxxx■■■■xxxxx■■x
xxxxxx■xxxxx■■■■■■xxx■■■■
xx■xxxxxxx■■■■■■■■xxx■■■■
x■■■xxxxxxxx■xxx■■xxxx■■x
■■■■■x■■■xxx■■xx■■xxxxxxx
x■■■xx■x■■xx■xxxx■xxxxxxx
x■x■xx■■x■■x■■xx■■xxxxxxx
■■■■■■■■■xx■x■xxx■■x■■■■■
xxxxxxxx■xx■■■■x■■■■xxxxx
x■xxxxxx■x■x■■■■■■xx■xxxx
■■■xxxxx■xx■x■■■■■xx■xxxx
■■■■xxxx■■xx■x■■■■■x■xxxx
■■■■■xxxx■■■■■x■■■■x■xxxx
■■■■■■■xxxxxx■■xxxxx■xxxx
■■■■■■■■■■xxx■■xx■■■■xxxx
■■■■■■■■■■■■■■■■■x■■■xxxx
■■■x■■■■x■■■■x■■■■xx■■■■■
x■x■■■■x■■■■x■■■■■xxx■■■■
■■■■xxxxxxxxxxxxx■xxxx■xx
xxxx■■■■■■■■■■■■■■■xx■■■■""";
        Puzzle puzzle = getTestPuzzle15ProfessionalLevel2();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    private Puzzle getTestPuzzle15ProfessionalLevel2() {
        var width = 25;
        var height = 20;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2,2,4,2));
        rows.add(List.of(1,6,4));
        rows.add(List.of(1,8,4));
        rows.add(List.of(3,1,2,2));
        rows.add(List.of(5,3,2,2));

        rows.add(List.of(3,1,2,1,1));
        rows.add(List.of(1,1,2,2,2,2));
        rows.add(List.of(9,1,1,2,5));
        rows.add(List.of(1,4,4));
        rows.add(List.of(1,1,1,6,1));

        rows.add(List.of(3,1,1,5,1));
        rows.add(List.of(4,2,1,5,1));
        rows.add(List.of(5,5,4,1));
        rows.add(List.of(7,2,1));
        rows.add(List.of(10,2,4));

        rows.add(List.of(17,3));
        rows.add(List.of(3,4,4,4,5));
        rows.add(List.of(1,4,4,5,4));
        rows.add(List.of(4,1,1));
        rows.add(List.of(15,4));

        cols.add(List.of(1,1,7,1));
        cols.add(List.of(5,10));
        cols.add(List.of(4,1,7,1));
        cols.add(List.of(5,5,2));
        cols.add(List.of(1,1,1,6,1));

        cols.add(List.of(1,1,5,1));
        cols.add(List.of(1,4,5,1));
        cols.add(List.of(1,1,2,3,1));
        cols.add(List.of(1,2,5,2,1,1));
        cols.add(List.of(2,2,4,1));

        cols.add(List.of(1,1,1,1,3,1));
        cols.add(List.of(1,2,1,1,3,1));
        cols.add(List.of(6,2,2,2,1));
        cols.add(List.of(3,1,5,4,1,1));
        cols.add(List.of(3,4,5,1));

        cols.add(List.of(3,4,3,1));
        cols.add(List.of(5,1,5,3,1));
        cols.add(List.of(12,1,4));
        cols.add(List.of(2,2,2,1));
        cols.add(List.of(1,2));

        cols.add(List.of(1,8));
        cols.add(List.of(2,1,2,1));
        cols.add(List.of(4,1,4));
        cols.add(List.of(4,1,2,1));
        cols.add(List.of(2,1,2,1));
        return new Puzzle(width, height, rows, cols);
    }
}
