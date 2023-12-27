package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase012Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxx■x■■xxxxxx
xxxx■■■■■■■xxxx
xxxx■■■■■■■xxxx
xx■■■■■■■x■■■xx
x■■■■■■■xxxx■■x
■■x■■■■■xxxxx■■
■xxx■■■■xxxxxx■
■xxxxx■■■■■xxx■
■xxxxxx■■■■■xx■
■■xxxxxxxxx■■■■
x■■■xxxxxxx■■■x
x■■■■■■■■■■■■■x
xx■■■■■■■■■■■xx
xxx■■■■■■■■■xxx
xxxxxxx■■■■xxxx""";
        Puzzle before = getTestPuzzle12();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle12() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1,2));
        rows.add(List.of(7));
        rows.add(List.of(7));
        rows.add(List.of(7,3));
        rows.add(List.of(7,2));

        rows.add(List.of(2,5,2));
        rows.add(List.of(1,4,1));
        rows.add(List.of(1,5,1));
        rows.add(List.of(1,5,1));
        rows.add(List.of(2,4));

        rows.add(List.of(3,3));
        rows.add(List.of(13));
        rows.add(List.of(11));
        rows.add(List.of(9));
        rows.add(List.of(4));

        cols.add(List.of(5));
        cols.add(List.of(2,3));
        cols.add(List.of(2,3));
        cols.add(List.of(3,4));
        cols.add(List.of(6,3));

        cols.add(List.of(7,3));
        cols.add(List.of(7,3));
        cols.add(List.of(9,4));
        cols.add(List.of(4,2,4));
        cols.add(List.of(2,2,4));

        cols.add(List.of(3,2,4));
        cols.add(List.of(1,6));
        cols.add(List.of(2,4));
        cols.add(List.of(2,3));
        cols.add(List.of(5));
        return new Puzzle(width, height, rows, cols);
    }
}
