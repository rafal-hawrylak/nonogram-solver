package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolveWholeCase020Test extends PuzzleSolverTestBase {

    @Test
    @Disabled("not finishing")
    void test() {
        String expectedPuzzle = """
""";
        Puzzle puzzle = getTestPuzzle19ProfessionalLevel2();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);

        System.out.println(puzzle.compact());
    }

    private Puzzle getTestPuzzle19ProfessionalLevel2() {
        var width = 35;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5,4));
        rows.add(List.of(3,2,2,3));
        rows.add(List.of(2,1,1,1,1,2));
        rows.add(List.of(1,1,1,1));
        rows.add(List.of(2,1,1,1));

        rows.add(List.of(1,1,2,9));
        rows.add(List.of(3,4,2,1));
        rows.add(List.of(5,2,1,2,1,1,1,1));
        rows.add(List.of(5,2,1,2,1,1,10));
        rows.add(List.of(3,3,2,1,2,2,2));

        rows.add(List.of(1,2,11,2,5));
        rows.add(List.of(1,1,18,8));
        rows.add(List.of(30,1));
        rows.add(List.of(5,13,1,2,2));
        rows.add(List.of(5,1,1,1,2,5,4,2));

        rows.add(List.of(5,1,1,1,1,3,4,4,4));
        rows.add(List.of(2,2,1,1,1,1,5,3,4,2,1));
        rows.add(List.of(1,1,15,5,2,1));
        rows.add(List.of(1,1,7,2,1,7));
        rows.add(List.of(2,6,3,2,4,2));

        rows.add(List.of(5,8,6));
        rows.add(List.of(5,4,2));
        rows.add(List.of(3,7));
        rows.add(List.of(5));
        rows.add(List.of(3));

        cols.add(List.of(5,1,1,8));
        cols.add(List.of(3,6,6,4));
        cols.add(List.of(2,5,4,3));
        cols.add(List.of(1,1,18));
        cols.add(List.of(2,1,2,5,3));

        cols.add(List.of(3,2,2,1,1));
        cols.add(List.of(1,2,4,1));
        cols.add(List.of(1,2,1,1));
        cols.add(List.of(3,2,4,2));
        cols.add(List.of(2,1,7,1,1));

        cols.add(List.of(1,1,8,5,1));
        cols.add(List.of(2,2,4,2,1));
        cols.add(List.of(3,1,13,1));
        cols.add(List.of(3,5,6));
        cols.add(List.of(6,8));

        cols.add(List.of(7,10));
        cols.add(List.of(2,4,4,4));
        cols.add(List.of(2,4,3,3));
        cols.add(List.of(1,1,4,3,3));
        cols.add(List.of(1,1,5,6));

        cols.add(List.of(1,1,5,2));
        cols.add(List.of(1,1,7,1));
        cols.add(List.of(1,1,9,1));
        cols.add(List.of(1,9,1));
        cols.add(List.of(1,2,1,1,2));

        cols.add(List.of(4,6,1));
        cols.add(List.of(1,2,3,1));
        cols.add(List.of(1,2,3,2));
        cols.add(List.of(1,6,1));
        cols.add(List.of(1,4,1));

        cols.add(List.of(1,3,4));
        cols.add(List.of(1,2,6));
        cols.add(List.of(4,3,2));
        cols.add(List.of(4,1,4));
        cols.add(List.of(1));
        return new Puzzle(width, height, rows, cols);
    }
}
