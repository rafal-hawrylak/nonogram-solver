package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase023Test extends PuzzleSolverTestBase {

    @Test
//    @Disabled("unsolved")
    void test() {
        String expectedPuzzle = """
""";
        Puzzle before = getTestPuzzle19ProfessionalLevel2();
        solveAndAssertSystem(before, expectedPuzzle);
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 25;
        var height = 35;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(3));
        rows.add(List.of(1, 4));
        rows.add(List.of(3, 5));
        rows.add(List.of(7, 5));

        rows.add(List.of(1, 1, 1, 3));
        rows.add(List.of(1, 1, 2, 1, 1));
        rows.add(List.of(1, 1, 1, 2, 1));
        rows.add(List.of(1, 1, 1, 2, 1, 1));
        rows.add(List.of(2, 1, 1, 1, 1, 2));

        rows.add(List.of(2, 3, 4, 4));
        rows.add(List.of(3, 3, 3, 2, 2));
        rows.add(List.of(3, 3, 4, 1, 1, 4));
        rows.add(List.of(3, 3, 2, 1, 2, 2));
        rows.add(List.of(3, 3, 3, 3, 2, 1, 1));

        rows.add(List.of(3, 3, 2, 3, 2));
        rows.add(List.of(3, 3, 3, 3, 4));
        rows.add(List.of(6, 2, 2, 2));
        rows.add(List.of(6, 2, 1, 1));
        rows.add(List.of(5, 1, 2, 2));

        rows.add(List.of(6, 2, 1, 2));
        rows.add(List.of(16));
        rows.add(List.of(1, 1));
        rows.add(List.of(1, 1));
        rows.add(List.of(16));

        rows.add(List.of(14));
        rows.add(List.of(1, 5));
        rows.add(List.of(14));
        rows.add(List.of(5, 12));
        rows.add(List.of(14));

        rows.add(List.of(1, 4));
        rows.add(List.of(12));
        rows.add(List.of(1, 5));
        rows.add(List.of(12));
        rows.add(List.of(10));

        cols.add(List.of(5, 1));
        cols.add(List.of(6, 1));
        cols.add(List.of(6, 1));
        cols.add(List.of(6, 5, 1));
        cols.add(List.of(7, 6));

        cols.add(List.of(5, 2, 1, 5));
        cols.add(List.of(19, 2, 1, 1, 1, 2));
        cols.add(List.of(3, 12, 2, 1, 1, 1, 2));
        cols.add(List.of(19, 2, 1, 1, 1, 2));
        cols.add(List.of(1, 1, 2, 1, 1, 1, 2));

        cols.add(List.of(1, 1, 2, 1, 1, 1, 2));
        cols.add(List.of(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2));
        cols.add(List.of(18, 2, 1, 1, 4));
        cols.add(List.of(8, 1, 11));
        cols.add(List.of(2, 6, 3, 11));

        cols.add(List.of(6, 6, 1, 11));
        cols.add(List.of(5, 9, 1, 10));
        cols.add(List.of(12, 3, 2, 6));
        cols.add(List.of(4, 1, 1, 6, 1));
        cols.add(List.of(2, 1, 3, 1));

        cols.add(List.of(4, 2, 1));
        cols.add(List.of(2, 1, 1));
        cols.add(List.of(1, 1, 1, 1));
        cols.add(List.of(2, 2, 1));
        cols.add(List.of(3, 1));
        return new Puzzle(width, height, rows, cols);
    }
}
