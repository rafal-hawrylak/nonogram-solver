package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase004Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
x■■■■■xxxx
■■xx■xxxxx
■■x■■x■xxx
■■■■■■■x■■
■■■■xx■xx■
■x■■■■■■■■
■■■■■■■■■■
■■■■■■■x■■
■x■■x■■■x■
■■■xx■■■■■""";
        Puzzle before = getTestPuzzle4();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }
    private Puzzle getTestPuzzle4() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5));
        rows.add(List.of(2, 1));
        rows.add(List.of(2, 2, 1));
        rows.add(List.of(7, 2));
        rows.add(List.of(4, 1, 1));
        rows.add(List.of(1, 8));
        rows.add(List.of(10));
        rows.add(List.of(7, 2));
        rows.add(List.of(1, 2, 3, 1));
        rows.add(List.of(3, 5));
        cols.add(List.of(9));
        cols.add(List.of(5, 2, 1));
        cols.add(List.of(1, 7));
        cols.add(List.of(1, 7));
        cols.add(List.of(4, 3));
        cols.add(List.of(1, 1, 5));
        cols.add(List.of(8));
        cols.add(List.of(2, 2));
        cols.add(List.of(1, 3, 1));
        cols.add(List.of(7));
        return new Puzzle(width, height, rows, cols);
    }
}
