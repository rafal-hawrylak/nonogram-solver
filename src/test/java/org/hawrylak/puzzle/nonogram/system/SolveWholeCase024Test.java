package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase024Test extends PuzzleSolverTestBase {

    @Test
//    @Disabled("unsolved")
    void test() {
        String expectedPuzzle = """
""";
        Puzzle before = getTestPuzzle19ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 30;
        var height = 40;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(10));
        rows.add(List.of(4, 1, 4));
        rows.add(List.of(4, 3, 5));
        rows.add(List.of(4, 5, 5));
        rows.add(List.of(4, 5, 5));

        rows.add(List.of(5, 7, 5));
        rows.add(List.of(4, 7, 6));
        rows.add(List.of(5, 7, 5, 1));
        rows.add(List.of(4, 7, 6, 1));
        rows.add(List.of(5, 14, 2, 1));

        rows.add(List.of(5, 10, 2, 1, 1));
        rows.add(List.of(8, 1, 1, 1, 1, 1));
        rows.add(List.of(6, 1, 1, 1, 1, 1));
        rows.add(List.of(2, 2, 2, 1, 1, 2, 1));
        rows.add(List.of(2, 1, 1, 2, 1, 1, 1));

        rows.add(List.of(2, 2, 2, 1, 1, 1, 1));
        rows.add(List.of(2, 2, 1, 1, 1, 1, 1));
        rows.add(List.of(2, 2, 2, 1, 2, 2, 1));
        rows.add(List.of(2, 2, 1, 2, 1, 1, 1));
        rows.add(List.of(2, 2, 2, 1, 1, 1, 1));

        rows.add(List.of(2, 2, 1, 1, 1, 2, 1));
        rows.add(List.of(1, 2, 2, 2, 1, 2, 1, 1));
        rows.add(List.of(1, 2, 2, 1, 1, 1, 2, 1, 1));
        rows.add(List.of(1, 1, 2, 3, 1, 1, 1, 1, 1));
        rows.add(List.of(1, 1, 3, 4, 1, 2, 1, 1));

        rows.add(List.of(2, 2, 7, 1));
        rows.add(List.of(4, 3, 9, 1));
        rows.add(List.of(5, 3, 11));
        rows.add(List.of(4, 2, 16));
        rows.add(List.of(1, 3, 2, 13, 1));

        rows.add(List.of(1, 3, 12, 1, 4));
        rows.add(List.of(1, 1, 3, 13, 2));
        rows.add(List.of(1, 1, 3, 14, 2));
        rows.add(List.of(1, 1, 11, 6, 1));
        rows.add(List.of(1, 1, 12, 2));

        rows.add(List.of(1, 1, 21));
        rows.add(List.of(1, 1));
        rows.add(List.of(1, 1, 5, 5, 1, 1));
        rows.add(List.of(1, 1, 2, 1, 2, 1, 1, 1, 1, 1));
        rows.add(List.of(1, 1, 1, 1, 1, 1));

        cols.add(List.of(6));
        cols.add(List.of(9, 4, 9));
        cols.add(List.of(8, 2, 2));
        cols.add(List.of(9, 2, 2, 2, 7, 3));
        cols.add(List.of(11, 2, 3));

        cols.add(List.of(6, 5, 2, 9));
        cols.add(List.of(5, 1, 2, 2, 9));
        cols.add(List.of(3, 2, 2, 2, 8, 2));
        cols.add(List.of(3, 1, 2, 2, 2, 3, 2));
        cols.add(List.of(2, 6, 2, 2, 3, 3, 1));

        cols.add(List.of(1, 11, 2, 2, 6, 3, 2));
        cols.add(List.of(1, 9, 3, 2, 1, 9, 1));
        cols.add(List.of(11, 3, 2, 2, 6, 2));
        cols.add(List.of(1, 9, 3, 2, 2, 7, 2));
        cols.add(List.of(1, 8, 3, 2, 10));

        cols.add(List.of(1, 10, 15));
        cols.add(List.of(2, 2, 5, 9, 2));
        cols.add(List.of(3, 1, 15, 1));
        cols.add(List.of(3, 1, 8, 1));
        cols.add(List.of(4, 1, 12, 1));

        cols.add(List.of(5, 2, 5, 8, 1, 2));
        cols.add(List.of(16, 10, 1, 1));
        cols.add(List.of(7, 3, 3, 3, 1, 2));
        cols.add(List.of(5, 3, 4, 2, 1, 1));
        cols.add(List.of(4, 4, 3, 3, 1, 2));

        cols.add(List.of(4, 5, 4, 1, 1));
        cols.add(List.of(6, 3, 1, 1, 3, 3));
        cols.add(List.of(1, 1, 1, 1));
        cols.add(List.of(20, 1, 1, 2, 4));
        cols.add(List.of(2));
        return new Puzzle(width, height, rows, cols);
    }
}
