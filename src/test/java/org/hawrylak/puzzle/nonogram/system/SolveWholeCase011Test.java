package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase011Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xx■x■xxxxxxxxxx
■■x■■■xxxxxxxxx
■■■■■xxxxxxxxxx
xxx■■xxxxxxxxxx
xxx■■■xxxxxxxxx
xxx■■■xxxxxxxxx
xxxx■■■xxxxxxxx
xxxx■■■■■■xxxxx
xxxxx■■■■■■■■■x
xxxxx■■■■■■■■■■
xxxxx■■■■■■■■■■
xxxxx■■■■■■■■■■
xxxxx■■■■■■■■■■
xxxxx■x■xxx■x■■
xxxxx■x■xxx■x■x""";
        Puzzle before = getTestPuzzle11();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle11() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1,1));
        rows.add(List.of(2,3));
        rows.add(List.of(5));
        rows.add(List.of(2));
        rows.add(List.of(3));
        rows.add(List.of(3));
        rows.add(List.of(3));
        rows.add(List.of(6));
        rows.add(List.of(9));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(1,1,1,2));
        rows.add(List.of(1,1,1,1));
        cols.add(List.of(2));
        cols.add(List.of(2));
        cols.add(List.of(1,1));
        cols.add(List.of(5));
        cols.add(List.of(8));
        cols.add(List.of(1,11));
        cols.add(List.of(7));
        cols.add(List.of(8));
        cols.add(List.of(6));
        cols.add(List.of(6));
        cols.add(List.of(5));
        cols.add(List.of(7));
        cols.add(List.of(5));
        cols.add(List.of(7));
        cols.add(List.of(5));
        return new Puzzle(width, height, rows, cols);
    }
}
