package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolveWholeCase018NeedsShootingTest extends PuzzleSolverTestBase {

    @Test
    @Disabled("needs shooting")
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
        Puzzle before = getTestPuzzle17ProfessionalLevel2();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
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
