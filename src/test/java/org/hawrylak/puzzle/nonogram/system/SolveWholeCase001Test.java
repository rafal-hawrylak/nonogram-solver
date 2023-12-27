package org.hawrylak.puzzle.nonogram.system;

import org.hawrylak.puzzle.nonogram.PuzzleSolver;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolverTestBase;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.solution.Solution;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolveWholeCase001Test extends PuzzleSolverTestBase {

    @Test
    void test() {
        String expectedPuzzle = """
xxxxx■■xxx
xxxx■■xxxx
x■■■■■xxxx
■■■xx■■xxx
■x■xx■■■xx
■x■xx■■■■x
■x■xx■■■■x
■■■xx■■■■■
x■■■■■■■■■
xxxxx■■■■■""";
        Puzzle before = getTestPuzzle1();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle1() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2));
        rows.add(List.of(2));
        rows.add(List.of(5));
        rows.add(List.of(3, 2));
        rows.add(List.of(1, 1, 3));
        rows.add(List.of(1, 1, 4));
        rows.add(List.of(1, 1, 4));
        rows.add(List.of(3, 5));
        rows.add(List.of(9));
        rows.add(List.of(5));
        cols.add(List.of(5));
        cols.add(List.of(2, 2));
        cols.add(List.of(7));
        cols.add(List.of(1, 1));
        cols.add(List.of(2, 1));
        cols.add(List.of(10));
        cols.add(List.of(1, 7));
        cols.add(List.of(6));
        cols.add(List.of(5));
        cols.add(List.of(3));
        return new Puzzle(width, height, rows, cols);
    }


    @Test
    void testWithInitialMarks() {
        String expectedPuzzle = """
xxxxx■■xxx
xxxx■■xxxx
x■■■■■xxxx
■■■xx■■xxx
■x■xx■■■xx
■x■xx■■■■x
■x■xx■■■■x
■■■xx■■■■■
x■■■■■■■■■
xxxxx■■■■■""";
        Puzzle before = getTestPuzzle1WithInitialMarks();
        print("before", before);

        Solution solution = new PuzzleSolver().solve(before);

        print("after", solution.getPuzzle());
        assertTrue(solution.isSolved());
        assertPuzzle(solution.getPuzzle(), expectedPuzzle);

        System.out.println(solution.getPuzzle().compact());
    }

    private Puzzle getTestPuzzle1WithInitialMarks() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2));
        rows.add(List.of(2));
        rows.add(List.of(5));
        rows.add(List.of(3, 2));
        rows.add(List.of(1, 1, 3));
        rows.add(List.of(1, 1, 4));
        rows.add(List.of(1, 1, 4));
        rows.add(List.of(3, 5));
        rows.add(List.of(9));
        rows.add(List.of(5));
        cols.add(List.of(5));
        cols.add(List.of(2, 2));
        cols.add(List.of(7));
        cols.add(List.of(1, 1));
        cols.add(List.of(2, 1));
        cols.add(List.of(10));
        cols.add(List.of(1, 7));
        cols.add(List.of(6));
        cols.add(List.of(5));
        cols.add(List.of(3));
        var puzzle = new Puzzle(width, height, rows, cols);
        // initial marks
        //puzzle.fields[c][r]
        puzzle.fields[2][0] = FieldState.EMPTY;
        puzzle.fields[3][0] = FieldState.EMPTY;
        puzzle.fields[4][0] = FieldState.EMPTY;
        puzzle.fields[7][0] = FieldState.EMPTY;
        puzzle.fields[8][0] = FieldState.EMPTY;
        puzzle.fields[9][0] = FieldState.EMPTY;
        puzzle.fields[0][1] = FieldState.EMPTY;
        puzzle.fields[1][1] = FieldState.EMPTY;
        puzzle.fields[9][1] = FieldState.EMPTY;
        puzzle.fields[8][2] = FieldState.EMPTY;
        puzzle.fields[9][2] = FieldState.EMPTY;
        puzzle.fields[3][3] = FieldState.EMPTY;
        puzzle.fields[7][3] = FieldState.EMPTY;
        puzzle.fields[8][3] = FieldState.EMPTY;
        puzzle.fields[9][3] = FieldState.EMPTY;
        puzzle.fields[1][4] = FieldState.EMPTY;
        puzzle.fields[4][4] = FieldState.EMPTY;
        puzzle.fields[4][5] = FieldState.EMPTY;
        puzzle.fields[3][6] = FieldState.EMPTY;
        puzzle.fields[4][7] = FieldState.EMPTY;
        puzzle.fields[0][9] = FieldState.EMPTY;
        puzzle.fields[1][9] = FieldState.EMPTY;
        puzzle.fields[2][9] = FieldState.EMPTY;
        puzzle.fields[4][9] = FieldState.EMPTY;
        return puzzle;
    }
}
