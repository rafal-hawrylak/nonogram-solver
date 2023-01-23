package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.utils.PuzzleSolver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SolveWholeCase013Test extends PuzzleSolverTestBase {

    @Test
    @Disabled("failing")
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
        Puzzle puzzle = getTestPuzzle12ProfessionalLevel1();
        print("before", puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        print("after", puzzle);
        assertTrue(solved);
        assertPuzzle(puzzle, expectedPuzzle);
    }

    private Puzzle getTestPuzzle12ProfessionalLevel1() {
        var width = 25;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(3,3));
        rows.add(List.of(2,4));
        rows.add(List.of(3,4,1));
        rows.add(List.of(3,5));
        rows.add(List.of(2,4));

        rows.add(List.of(2,3));
        rows.add(List.of(1,2,3,1));
        rows.add(List.of(2,1,1,5));
        rows.add(List.of(3,1,4));
        rows.add(List.of(2,2,3,1));

        rows.add(List.of(1,2,1,4,2));
        rows.add(List.of(2,2,9));
        rows.add(List.of(5,8));
        rows.add(List.of(2,6,8));
        rows.add(List.of(14));

        rows.add(List.of(4,4));
        rows.add(List.of(2,5,3));
        rows.add(List.of(9));
        rows.add(List.of(3,6,3));
        rows.add(List.of(16,5));

        rows.add(List.of(25));
        rows.add(List.of(13,11));
        rows.add(List.of(10,2,11));
        rows.add(List.of(2,4,4,10));
        rows.add(List.of(14));

        cols.add(List.of(1,1,3));
        cols.add(List.of(4,5));
        cols.add(List.of(2,2,2,5));
        cols.add(List.of(2,2,2,4));
        cols.add(List.of(3,4,5));

        cols.add(List.of(8,6));
        cols.add(List.of(4,7));
        cols.add(List.of(3,2,5));
        cols.add(List.of(2,8));
        cols.add(List.of(10));

        cols.add(List.of(8,1));
        cols.add(List.of(1,12));
        cols.add(List.of(3,3,8));
        cols.add(List.of(4,3,5,2));
        cols.add(List.of(3,4,2,4,1));

        cols.add(List.of(2,4,5,1,6));
        cols.add(List.of(3,5,3,6));
        cols.add(List.of(5,4,5));
        cols.add(List.of(13,5));
        cols.add(List.of(11,5));

        cols.add(List.of(10,6));
        cols.add(List.of(2,2,1,6));
        cols.add(List.of(2,2,7));
        cols.add(List.of(2,7));
        cols.add(List.of(7));
        return new Puzzle(width, height, rows, cols);
    }
}