package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase017Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxx■■■■■xxxxxxxxxx
xxxx■■■■■■■■xxxxxxxx
xxxx■■■■■■■■■xxxxxxx
xxx■■■■■■■■■■xxxxxxx
xxx■■■■x■■■■■xxxxxxx
xxx■■■x■■xx■■■xxxxxx
x■x■■■xxxxxx■■xxxxxx
x■■■■xxxxxxx■■xxxxxx
x■■■■■x■■x■■■■xxxxxx
xx■■■■xxxxxx■x■■■xxx
xxxx■xxxx■x■■■■■■■xx
x■■■■■xxxx■■■■■x■■■x
■■x■■■xx■■■■■■x■■■■x
■x■■■■x■■■■■x■x■■■■■
■x■■■x■■■■■x■■■■■x■■
■■■■x■■■■■■x■■■■■■x■
■■■■x■■■■■■■■■■■■■x■
■■■x■■■■x■■■■■x■■■■x
■x■x■■■x■■■■■xxx■x■x
■■x■■■■x■■■xxxxx■■xx
x■x■x■■■■■xxx■xxx■xx
xxx■x■■■■■xxx■xxx■xx
xxx■xx■■x■■xx■xxx■xx
xx■■x■x■■x■■x■■xx■■x
xx■xx■x■xxxx■■■xxx■x
xx■xx■x■x■xx■x■■xx■x
x■■x■■x■xxxx■xx■xx■■
x■xx■xx■x■x■■xx■xxx■
■■x■■xx■xxx■xx■■xxx■
■xx■xxx■x■x■x■■xxx■■""";
        Puzzle before = getTestPuzzle16ProfessionalLevel2();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle16ProfessionalLevel2() {
        var width = 20;
        var height = 30;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5));
        rows.add(List.of(8));
        rows.add(List.of(9));
        rows.add(List.of(10));
        rows.add(List.of(4,5));

        rows.add(List.of(3,2,3));
        rows.add(List.of(1,3,2));
        rows.add(List.of(4,2));
        rows.add(List.of(5,2,4));
        rows.add(List.of(4,1,3));

        rows.add(List.of(1,1,7));
        rows.add(List.of(5,5,3));
        rows.add(List.of(2,3,6,4));
        rows.add(List.of(1,4,5,1,5));
        rows.add(List.of(1,3,5,5,2));

        rows.add(List.of(4,6,6,1));
        rows.add(List.of(4,13,1));
        rows.add(List.of(3,4,5,4));
        rows.add(List.of(1,1,3,5,1,1));
        rows.add(List.of(2,4,3,2));

        rows.add(List.of(1,1,5,1,1));
        rows.add(List.of(1,5,1,1));
        rows.add(List.of(1,2,2,1,1));
        rows.add(List.of(2,1,2,2,2,2));
        rows.add(List.of(1,1,1,3,1));

        rows.add(List.of(1,1,1,1,1,2,1));
        rows.add(List.of(2,2,1,1,1,2));
        rows.add(List.of(1,1,1,1,2,1,1));
        rows.add(List.of(2,2,1,1,2,1));
        rows.add(List.of(1,1,1,1,1,2,2));

        cols.add(List.of(8,2));
        cols.add(List.of(3,2,3,2,3));
        cols.add(List.of(3,1,6,4));
        cols.add(List.of(7,6,5,2));
        cols.add(List.of(14,3,3));

        cols.add(List.of(7,2,3,7,4));
        cols.add(List.of(5,9));
        cols.add(List.of(4,1,1,5,10));
        cols.add(List.of(6,1,5,4,1));
        cols.add(List.of(5,1,11,1,1,1));

        cols.add(List.of(4,1,9,2));
        cols.add(List.of(5,1,4,3,1,3));
        cols.add(List.of(11,5,4));
        cols.add(List.of(4,8,5,1));
        cols.add(List.of(3,3,3,2));

        cols.add(List.of(2,6,4));
        cols.add(List.of(11));
        cols.add(List.of(4,3,5));
        cols.add(List.of(4,2,4,1));
        cols.add(List.of(4,4));
        return new Puzzle(width, height, rows, cols);
    }
}
