package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SolveWholeCase021Test extends PuzzleSolverTestBase {

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
        var height = 35;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(4));
        rows.add(List.of(6));
        rows.add(List.of(3,4));
        rows.add(List.of(2,3));
        rows.add(List.of(3,3));

        rows.add(List.of(4,5));
        rows.add(List.of(3,3));
        rows.add(List.of(3,4));
        rows.add(List.of(4,4,4));
        rows.add(List.of(4,2,5));

        rows.add(List.of(5,6));
        rows.add(List.of(6,6));
        rows.add(List.of(7,5));
        rows.add(List.of(5,1,1,4));
        rows.add(List.of(2,2,1,1,2,5));

        rows.add(List.of(4,1,1,1,1,1,2));
        rows.add(List.of(1,1,6,1,1,2,7));
        rows.add(List.of(1,1,1,1,1,2,2,1,1,1));
        rows.add(List.of(1,5,1,1,3,2,3,4));
        rows.add(List.of(2,1,1,1,2,3,1,1,1,1,1));

        rows.add(List.of(10,1,3,1,8));
        rows.add(List.of(10,7,9));
        rows.add(List.of(10,5,8));
        rows.add(List.of(9,4,7));
        rows.add(List.of(8,4,2,1));

        rows.add(List.of(2,3,4,3,1));
        rows.add(List.of(1,4,6,1,1));
        rows.add(List.of(1,1,2,2,1,1));
        rows.add(List.of(1,1,1,2,1,1,3));
        rows.add(List.of(1,2,1,1,2,1,7));

        rows.add(List.of(8,3,3,7));
        rows.add(List.of(7,9,5));
        rows.add(List.of(5,9,1));
        rows.add(List.of(1,10,1));
        rows.add(List.of(1,10,1));

        cols.add(List.of(5,3));
        cols.add(List.of(10,3));
        cols.add(List.of(1,13));
        cols.add(List.of(1,8,4));
        cols.add(List.of(5,5,3));

        cols.add(List.of(2,1,5,1,3));
        cols.add(List.of(1,3,7,2,2));
        cols.add(List.of(1,1,13));
        cols.add(List.of(4,1,1,7));
        cols.add(List.of(9,7));

        cols.add(List.of(9,3));
        cols.add(List.of(11,4,4));
        cols.add(List.of(5,3,3,7));
        cols.add(List.of(3,1,1,7,2,2,5));
        cols.add(List.of(2,2,1,11,5));

        cols.add(List.of(3,2,1,9,2,4));
        cols.add(List.of(4,1,1,10,2,4));
        cols.add(List.of(5,8,7,5));
        cols.add(List.of(11,4,2,5));
        cols.add(List.of(9,3,7));

        cols.add(List.of(8,3,2));
        cols.add(List.of(8,3));
        cols.add(List.of(3,1,11));
        cols.add(List.of(1,3,6,2,3));
        cols.add(List.of(2,1,6,1,3));

        cols.add(List.of(3,4,3));
        cols.add(List.of(1,1,7,3));
        cols.add(List.of(1,1,1,4,1,4));
        cols.add(List.of(3,1,3,6));
        cols.add(List.of(7,3));
        return new Puzzle(width, height, rows, cols);
    }
}
