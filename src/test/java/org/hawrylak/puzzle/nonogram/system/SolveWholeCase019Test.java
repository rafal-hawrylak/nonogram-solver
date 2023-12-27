package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase019Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxxxx■■■■■■■■■■■■■■■■■■■■xxx
xxxxxxxxxx■■■xxxxxxxx■■■■■■■■■■■■xx
xxxxxxxxx■■xxxxxxxxxxxxx■■■■■■■■■■x
xxxxxxxx■■xxxxxxxxxxxxxx■xxx■xx■■■x
xxxxxxx■■xxxxxxxxxxxxxx■■xxx■■xx■■x
xxxxxx■■xxxxxxx■■■■■xxx■■xxxx■xx■■x
xxxxx■■■■■■■xx■■xxx■■x■■xxxxx■xx■■x
xxx■■■x■■■■■■■■xxxxx■x■■xxxxx■xx■■x
xx■■■■x■■■■■■■■■■■■■■■■xxxxx■■■■■■x
x■■■■xx■■■■■■■■■■■■xx■■■■■■■■x■■■■■
■■■■■x■■■■■■■■■xxxxx■■■■■■■■■x■■xx■
■xxx■■■■■■■■xxxx■■■■■■■■■■■x■x■xx■x
■xx■■xx■■■■xx■■■■■■■■xxxxx■■■x■x■■■
■■■■■xxxxxx■■xxxx■■■xxxxxxx■■x■x■■■
■xx■■xxxxxxx■xxxxx■■xx■■■xx■■x■x■x■
■xxxx■■■■xxx■■■■■■■xx■xxx■x■■x■x■x■
■xxxxxxx■■■■■■■■■■■xx■■■xx■■■■■x■x■
■■xxxxxxxxxxxxxxxx■x■■■■■x■■■xx■■■■
x■■■xxxxxxxxxxxxxx■x■■■x■■xxx■■■■■■
x■x■■■■■■xxxxxxxxx■x■■xxx■xxxx■■■■x
x■■x■■■■■■■■■■■■■■■■x■xxx■xxxxxxxxx
xx■■xx■■xxxxxxxxx■■■x■xxx■xxxxxxxxx
xxx■■■■xxxxxxxxxx■■■x■■x■■xxxxxxxxx
xxxxxxxxxxxxxxxxxx■■■x■■■xxxxxxxxxx
xxxxxxxxxxxxxxxxxxx■■■■■xxxxxxxxxxx""";
        Puzzle before = getTestPuzzle18ProfessionalLevel2();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle18ProfessionalLevel2() {
        var width = 35;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(20));
        rows.add(List.of(3,12));
        rows.add(List.of(2,10));
        rows.add(List.of(2,1,1,3));
        rows.add(List.of(2,2,2,2));

        rows.add(List.of(2,5,2,1,2));
        rows.add(List.of(7,2,2,2,1,2));
        rows.add(List.of(3,8,1,2,1,2));
        rows.add(List.of(4,16,6));
        rows.add(List.of(4,12,8,5));

        rows.add(List.of(5,9,9,2,1));
        rows.add(List.of(1,8,11,1,1,1));
        rows.add(List.of(1,2,4,8,3,1,3));
        rows.add(List.of(5,2,3,2,1,3));
        rows.add(List.of(1,2,1,2,3,2,1,1,1));

        rows.add(List.of(1,4,7,1,1,2,1,1,1));
        rows.add(List.of(1,11,3,5,1,1));
        rows.add(List.of(2,1,5,3,4));
        rows.add(List.of(3,1,3,2,6));
        rows.add(List.of(1,6,1,2,1,4));

        rows.add(List.of(2,16,1,1));
        rows.add(List.of(2,2,3,1,1));
        rows.add(List.of(4,3,2,2));
        rows.add(List.of(3,3));
        rows.add(List.of(5));

        cols.add(List.of(8));
        cols.add(List.of(2,1,4));
        cols.add(List.of(3,1,1,2));
        cols.add(List.of(4,3,2,2));
        cols.add(List.of(8,2,1));

        cols.add(List.of(3,1,1,2,1));
        cols.add(List.of(2,2,1,4));
        cols.add(List.of(9,1,3));
        cols.add(List.of(2,7,2,2));
        cols.add(List.of(2,7,1,1));

        cols.add(List.of(2,7,1,1));
        cols.add(List.of(1,6,1,1,1));
        cols.add(List.of(2,4,4,1));
        cols.add(List.of(1,4,1,2,1));
        cols.add(List.of(1,5,1,2,1));

        cols.add(List.of(1,2,2,1,2,1));
        cols.add(List.of(1,1,2,2,2,1));
        cols.add(List.of(1,1,2,3,2,3));
        cols.add(List.of(1,1,2,13));
        cols.add(List.of(1,2,1,4,5));

        cols.add(List.of(1,3,3,3,2));
        cols.add(List.of(2,4,8,1));
        cols.add(List.of(2,6,1,3,3));
        cols.add(List.of(2,4,3,1,2,2));
        cols.add(List.of(6,3,1,2,2));

        cols.add(List.of(3,3,1,5));
        cols.add(List.of(3,4,2));
        cols.add(List.of(3,2,6));
        cols.add(List.of(5,10));
        cols.add(List.of(3,5,1,1));

        cols.add(List.of(3,9,2));
        cols.add(List.of(4,3,3));
        cols.add(List.of(9,8));
        cols.add(List.of(8,3,3));
        cols.add(List.of(2,7));
        return new Puzzle(width, height, rows, cols);
    }
}
