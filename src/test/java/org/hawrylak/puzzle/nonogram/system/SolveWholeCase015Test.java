package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.model.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase015Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxx■■■xxxxx■■■xxx
xxxxx■■x■■■xxx■x■xxx
xxxx■■xxx■■■■■■x■xxx
xxx■■xx■xx■■■■■x■xxx
xx■■xx■■■xx■■■■x■■xx
x■■xxxxxxxxx■■■■■■xx
■■■■■■■■■■■■■■■■■■■x
xxx■■xx■■xx■■x■■■■■■
xxx■■xx■■xx■■xxxx■■■
xxx■xxx■■xxx■■xxx■xx
xxx■xxx■■xxx■■xxx■xx
xx■■xxx■■xxx■■xxx■xx
xx■■xx■■■■xx■■xxx■■x
xx■■■■■■■■■■■■xxxx■x
xx■■■■■■■■■■■■xxxx■x
xx■xxx■■■■■■■■xxxx■x
xx■xxx■■xxx■■■xxxx■x
x■■xxx■xxxxx■■■xxx■x
x■■xxx■xxxxx■■■xxx■x
x■xxx■■xxxxx■■■xxx■x
x■xxx■■x■xxx■■■xxx■x
x■xxx■■xxxxx■■■xxx■x
x■■■■■■xxxxx■■■xxx■x
x■■■■■■xxxxx■■■xxx■x
x■■■■■■■■■■■■■■■■■■x""";
        Puzzle before = getTestPuzzle14ProfessionalLevel1();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle14ProfessionalLevel1() {
        var width = 20;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(3,3));
        rows.add(List.of(2,3,1,1));
        rows.add(List.of(2,6,1));
        rows.add(List.of(2,1,5,1));
        rows.add(List.of(2,3,4,2));

        rows.add(List.of(2,6));
        rows.add(List.of(19));
        rows.add(List.of(2,2,2,6));
        rows.add(List.of(2,2,2,3));
        rows.add(List.of(1,2,2,1));

        rows.add(List.of(1,2,2,1));
        rows.add(List.of(2,2,2,1));
        rows.add(List.of(2,4,2,2));
        rows.add(List.of(12,1));
        rows.add(List.of(12,1));

        rows.add(List.of(1,8,1));
        rows.add(List.of(1,2,3,1));
        rows.add(List.of(2,1,3,1));
        rows.add(List.of(2,1,3,1));
        rows.add(List.of(1,2,3,1));

        rows.add(List.of(1,2,1,3,1));
        rows.add(List.of(1,2,3,1));
        rows.add(List.of(6,3,1));
        rows.add(List.of(6,3,1));
        rows.add(List.of(18));

        cols.add(List.of(1));
        cols.add(List.of(2,8));
        cols.add(List.of(3,8,3));
        cols.add(List.of(2,9,3));
        cols.add(List.of(2,3,2,3));

        cols.add(List.of(2,1,2,6));
        cols.add(List.of(2,1,1,13));
        cols.add(List.of(1,2,11,1));
        cols.add(List.of(2,1,10,1,1));
        cols.add(List.of(2,1,4,1));

        cols.add(List.of(3,1,3,1));
        cols.add(List.of(3,3,4,1));
        cols.add(List.of(23));
        cols.add(List.of(5,16));
        cols.add(List.of(8,8));

        cols.add(List.of(1,3,1));
        cols.add(List.of(8,1));
        cols.add(List.of(9,1));
        cols.add(List.of(3,13));
        cols.add(List.of(2));

        return new Puzzle(width, height, rows, cols);
    }
}
