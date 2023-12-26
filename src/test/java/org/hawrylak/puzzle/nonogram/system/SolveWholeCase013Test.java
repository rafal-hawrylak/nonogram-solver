package org.hawrylak.puzzle.nonogram.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.Solution;
import org.junit.jupiter.api.Test;

public class SolveWholeCase013Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxxxxxxxx■■■x■■■xxxxxxx
xxxxxxxxxxxx■■x■■■■xxxxxx
xxxxxxxxxxxx■■■x■■■■x■xxx
xxxxxxxxxxxxx■■■x■■■■■xxx
xxxxxxxxxxxxxx■■x■■■■xxxx
xxxxxxxxxxxxxxx■■x■■■xxxx
xx■xxxxxxxxxxxx■■x■■■x■xx
xx■■x■xxxxxxxxxx■x■■■■■xx
xxx■■■xxxxxxxxxx■x■■■■xxx
xxxx■■xxxxxxxxx■■x■■■xx■x
xx■x■■xxxxxxxxx■x■■■■x■■x
xx■■x■■xxxxxxx■■■■■■■■■xx
xxx■■■■■xxxxx■■■■■■■■xxxx
■■xx■■■■■■x■■■■■■■■xxxxxx
x■■■■■■■■■■■■■■xxxxxxxxxx
x■■■■xxxx■■■■xxxxxxxxxxxx
■■xxxxx■■■■■x■■■xxxxxxxxx
xxxxxx■■■■■■■■■xxxxxxxxxx
xxxx■■■x■■■■■■xxxxxxxx■■■
x■■■■■■■■■■■■■■■■xxx■■■■■
■■■■■■■■■■■■■■■■■■■■■■■■■
■■■■■■■■■■■■■x■■■■■■■■■■■
■■■■■■■■■■x■■x■■■■■■■■■■■
x■■xx■■■■x■■■■x■■■■■■■■■■
xxxxxxxxxxx■■■■■■■■■■■■■■""";
        Puzzle before = getTestPuzzle12ProfessionalLevel1();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
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
