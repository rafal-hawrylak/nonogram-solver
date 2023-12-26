package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase002Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxx■■■x
■■■■■■■■xx
x■■■■■■■■x
xx■■■■■■■■
xxx■■■■■■■
xxxx■xx■■■
xxxxxxxx■■
xxx■x■■x■■
■■x■■■■■■■
■■■■■■■■■■""";
        Puzzle before = getTestPuzzle2();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }
    private Puzzle getTestPuzzle2() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(3));
        rows.add(List.of(8));
        rows.add(List.of(8));
        rows.add(List.of(8));
        rows.add(List.of(7));
        rows.add(List.of(1, 3));
        rows.add(List.of(2));
        rows.add(List.of(1, 2, 2));
        rows.add(List.of(2, 7));
        rows.add(List.of(10));
        cols.add(List.of(1, 2));
        cols.add(List.of(2, 2));
        cols.add(List.of(3, 1));
        cols.add(List.of(4, 3));
        cols.add(List.of(5, 2));
        cols.add(List.of(4, 3));
        cols.add(List.of(5, 3));
        cols.add(List.of(6, 2));
        cols.add(List.of(1, 8));
        cols.add(List.of(7));
        return new Puzzle(width, height, rows, cols);
    }
}
