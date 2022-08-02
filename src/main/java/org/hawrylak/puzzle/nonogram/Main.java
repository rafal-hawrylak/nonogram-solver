package org.hawrylak.puzzle.nonogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.solver.PuzzleSolver;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    @SneakyThrows
    private void run() {
        System.out.println("Hello Nonogram Solver!");
        Puzzle puzzle = getTestPuzzle17ProfessionalLevel2();
        System.out.println(puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        System.out.println("solved = " + solved);
        System.out.println(puzzle);
        System.out.println(puzzle.compact());
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
        var puzzle = new Puzzle(width, height, rows, cols);
        // initial marks
        //puzzle.fields[c][r]
//        puzzle.fields[2][0] = FieldState.EMPTY;
//        puzzle.fields[3][0] = FieldState.EMPTY;
//        puzzle.fields[4][0] = FieldState.EMPTY;
//        puzzle.fields[7][0] = FieldState.EMPTY;
//        puzzle.fields[8][0] = FieldState.EMPTY;
//        puzzle.fields[9][0] = FieldState.EMPTY;
//        puzzle.fields[0][1] = FieldState.EMPTY;
//        puzzle.fields[1][1] = FieldState.EMPTY;
//        puzzle.fields[9][1] = FieldState.EMPTY;
//        puzzle.fields[8][2] = FieldState.EMPTY;
//        puzzle.fields[9][2] = FieldState.EMPTY;
//        puzzle.fields[3][3] = FieldState.EMPTY;
//        puzzle.fields[7][3] = FieldState.EMPTY;
//        puzzle.fields[8][3] = FieldState.EMPTY;
//        puzzle.fields[9][3] = FieldState.EMPTY;
//        puzzle.fields[1][4] = FieldState.EMPTY;
//        puzzle.fields[4][4] = FieldState.EMPTY;
//        puzzle.fields[4][5] = FieldState.EMPTY;
//        puzzle.fields[3][6] = FieldState.EMPTY;
//        puzzle.fields[4][7] = FieldState.EMPTY;
//        puzzle.fields[0][9] = FieldState.EMPTY;
//        puzzle.fields[1][9] = FieldState.EMPTY;
//        puzzle.fields[2][9] = FieldState.EMPTY;
//        puzzle.fields[4][9] = FieldState.EMPTY;
        return puzzle;
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
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle3() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(10));
        rows.add(List.of(3, 3));
        rows.add(List.of(2, 3, 2));
        rows.add(List.of(1, 1, 2));
        rows.add(List.of(5, 1));
        rows.add(List.of(1, 1, 1));
        rows.add(List.of(1, 7));
        rows.add(List.of(1, 5));
        rows.add(List.of(6));
        rows.add(List.of(2));
        cols.add(List.of(4, 1));
        cols.add(List.of(3, 1));
        cols.add(List.of(2, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 1, 1));
        cols.add(List.of(1, 1, 1, 3));
        cols.add(List.of(1, 1, 1, 4));
        cols.add(List.of(2, 1, 4));
        cols.add(List.of(4, 3));
        cols.add(List.of(8));
        var puzzle = new Puzzle(width, height, rows, cols);
        // initial marks
        return puzzle;
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
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle5() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(8));
        rows.add(List.of(1, 1));
        rows.add(List.of(3, 2));
        rows.add(List.of(3, 6));
        rows.add(List.of(2, 2));
        rows.add(List.of(7, 2));
        rows.add(List.of(3, 2));
        rows.add(List.of(2, 2));
        rows.add(List.of(3, 3));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 7));
        cols.add(List.of(10));
        cols.add(List.of(1, 1, 2, 1));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 7));
        cols.add(List.of(5, 3));
        cols.add(List.of(1, 1, 1));
        cols.add(List.of(1, 1));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle6() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(7));
        rows.add(List.of(5));
        rows.add(List.of(11));
        rows.add(List.of(1, 1, 5, 1));
        rows.add(List.of(13));
        rows.add(List.of(2, 7));
        rows.add(List.of(2, 10));
        rows.add(List.of(1, 11, 1));
        rows.add(List.of(1, 11, 1));
        rows.add(List.of(15));
        rows.add(List.of(12));
        rows.add(List.of(12));
        rows.add(List.of(14));
        rows.add(List.of(7, 6));
        rows.add(List.of(8));
        cols.add(List.of(7, 1));
        cols.add(List.of(3, 1, 3));
        cols.add(List.of(3, 8));
        cols.add(List.of(1, 1, 9));
        cols.add(List.of(1, 1, 11));
        cols.add(List.of(15));
        cols.add(List.of(15));
        cols.add(List.of(15));
        cols.add(List.of(13));
        cols.add(List.of(14));
        cols.add(List.of(1, 1, 10));
        cols.add(List.of(1, 1, 8));
        cols.add(List.of(3, 8));
        cols.add(List.of(5));
        cols.add(List.of(3, 2));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle7() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(6,3));
        rows.add(List.of(3,6));
        rows.add(List.of(5,7));
        rows.add(List.of(13));
        rows.add(List.of(10));
        rows.add(List.of(14));
        rows.add(List.of(13));
        rows.add(List.of(6));
        rows.add(List.of(8));
        rows.add(List.of(6));
        rows.add(List.of(1,7));
        rows.add(List.of(3,8));
        rows.add(List.of(2,8,1));
        rows.add(List.of(10));
        rows.add(List.of(8));
        cols.add(List.of(1,2,1));
        cols.add(List.of(2,2,4));
        cols.add(List.of(3,2,4));
        cols.add(List.of(4,2,2));
        cols.add(List.of(7,2));
        cols.add(List.of(1,5,3));
        cols.add(List.of(1,4,3));
        cols.add(List.of(7,1,4));
        cols.add(List.of(7,1,4));
        cols.add(List.of(13));
        cols.add(List.of(14));
        cols.add(List.of(13));
        cols.add(List.of(13));
        cols.add(List.of(10));
        cols.add(List.of(6));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle8() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(15));
        rows.add(List.of(2,3,1));
        rows.add(List.of(5,3,1));
        rows.add(List.of(4,2));
        rows.add(List.of(4,3));
        rows.add(List.of(6,1,3));
        rows.add(List.of(6,1,3));
        rows.add(List.of(9,3,1));
        rows.add(List.of(1,3,2,3,1));
        rows.add(List.of(5,9));
        rows.add(List.of(13));
        rows.add(List.of(1,7,5));
        rows.add(List.of(1,7,1,3));
        rows.add(List.of(5,5,3));
        rows.add(List.of(1,1,1,1));
        cols.add(List.of(3,5,3));
        cols.add(List.of(8,1,1));
        cols.add(List.of(1,13));
        cols.add(List.of(1,12));
        cols.add(List.of(1,13));
        cols.add(List.of(1,3,3));
        cols.add(List.of(1,1,5));
        cols.add(List.of(2,9));
        cols.add(List.of(4,7));
        cols.add(List.of(4,2,1));
        cols.add(List.of(1,1,11));
        cols.add(List.of(1,8));
        cols.add(List.of(1,11));
        cols.add(List.of(1,5));
        cols.add(List.of(3,7));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle9() {
        var width = 10;
        var height = 10;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2));
        rows.add(List.of(4));
        rows.add(List.of(6));
        rows.add(List.of(6));
        rows.add(List.of(8));
        rows.add(List.of(8));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,2,3));
        rows.add(List.of(3,2,3));
        rows.add(List.of(8));
        cols.add(List.of(3));
        cols.add(List.of(6));
        cols.add(List.of(8));
        cols.add(List.of(5,1));
        cols.add(List.of(6,2));
        cols.add(List.of(6,3));
        cols.add(List.of(7,1));
        cols.add(List.of(4,2));
        cols.add(List.of(6));
        cols.add(List.of(3));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle10() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1));
        rows.add(List.of(2,3));
        rows.add(List.of(4,5));
        rows.add(List.of(4,7));
        rows.add(List.of(2,2,5));
        rows.add(List.of(2,6));
        rows.add(List.of(2,7));
        rows.add(List.of(2,8));
        rows.add(List.of(2,9));
        rows.add(List.of(2,10));
        rows.add(List.of(15));
        rows.add(List.of(13,1));
        rows.add(List.of(8,3,2));
        rows.add(List.of(4,3,1));
        rows.add(List.of(4));
        cols.add(List.of(4));
        cols.add(List.of(2,5));
        cols.add(List.of(4,7));
        cols.add(List.of(4,2,5));
        cols.add(List.of(2,2,3,1));
        cols.add(List.of(2,4,1));
        cols.add(List.of(2,5));
        cols.add(List.of(2,6));
        cols.add(List.of(2,6,1));
        cols.add(List.of(3,9));
        cols.add(List.of(14));
        cols.add(List.of(12));
        cols.add(List.of(10));
        cols.add(List.of(8,1));
        cols.add(List.of(10));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle11() {
        var width = 15;
        var height = 15;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(1,1));
        rows.add(List.of(2,3));
        rows.add(List.of(5));
        rows.add(List.of(2));
        rows.add(List.of(3));
        rows.add(List.of(3));
        rows.add(List.of(3));
        rows.add(List.of(6));
        rows.add(List.of(9));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(10));
        rows.add(List.of(1,1,1,2));
        rows.add(List.of(1,1,1,1));
        cols.add(List.of(2));
        cols.add(List.of(2));
        cols.add(List.of(1,1));
        cols.add(List.of(5));
        cols.add(List.of(8));
        cols.add(List.of(1,11));
        cols.add(List.of(7));
        cols.add(List.of(8));
        cols.add(List.of(6));
        cols.add(List.of(6));
        cols.add(List.of(5));
        cols.add(List.of(7));
        cols.add(List.of(5));
        cols.add(List.of(7));
        cols.add(List.of(5));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
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
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle13ProfessionalLevel1() {
        var width = 20;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5));
        rows.add(List.of(7));
        rows.add(List.of(7));
        rows.add(List.of(4,2));
        rows.add(List.of(10));

        rows.add(List.of(3,3,5));
        rows.add(List.of(2,2,3,2));
        rows.add(List.of(3,5,3));
        rows.add(List.of(2,7,2,1));
        rows.add(List.of(2,10,2,1));

        rows.add(List.of(1,13,1));
        rows.add(List.of(1,4,8,2));
        rows.add(List.of(2,3,2,2,5));
        rows.add(List.of(9,4,2,1));
        rows.add(List.of(1,5,5,2,1));

        rows.add(List.of(1,6,6,1));
        rows.add(List.of(2,2,2,2,1));
        rows.add(List.of(4,1,3,1,1));
        rows.add(List.of(1,3,1,2,1,2));
        rows.add(List.of(1,6,2));

        rows.add(List.of(1,2,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(3,1,2));
        rows.add(List.of(6));

        cols.add(List.of(9));
        cols.add(List.of(2,2,6));
        cols.add(List.of(2,1,1,1));
        cols.add(List.of(1,5,2,2));
        cols.add(List.of(2,7,1,1));

        cols.add(List.of(2,8,2,2));
        cols.add(List.of(1,4,3,1,1));
        cols.add(List.of(2,3,4,1,2));
        cols.add(List.of(1,3,2,6,1));
        cols.add(List.of(10,2,6));

        cols.add(List.of(11,2,2,1));
        cols.add(List.of(6,9,2,1));
        cols.add(List.of(5,1,9,2));
        cols.add(List.of(3,1,2,3,6,2));
        cols.add(List.of(3,3,3,1,2));

        cols.add(List.of(7,8,2));
        cols.add(List.of(5,2,7,2));
        cols.add(List.of(1,2,1,2));
        cols.add(List.of(3,8));
        cols.add(List.of(6));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
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

        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle15ProfessionalLevel2() {
        var width = 25;
        var height = 20;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(2,2,4,2));
        rows.add(List.of(1,6,4));
        rows.add(List.of(1,8,4));
        rows.add(List.of(3,1,2,2));
        rows.add(List.of(5,3,2,2));

        rows.add(List.of(3,1,2,1,1));
        rows.add(List.of(1,1,2,2,2,2));
        rows.add(List.of(9,1,1,2,5));
        rows.add(List.of(1,4,4));
        rows.add(List.of(1,1,1,6,1));

        rows.add(List.of(3,1,1,5,1));
        rows.add(List.of(4,2,1,5,1));
        rows.add(List.of(5,5,4,1));
        rows.add(List.of(7,2,1));
        rows.add(List.of(10,2,4));

        rows.add(List.of(17,3));
        rows.add(List.of(3,4,4,4,5));
        rows.add(List.of(1,4,4,5,4));
        rows.add(List.of(4,1,1));
        rows.add(List.of(15,4));

        cols.add(List.of(1,1,7,1));
        cols.add(List.of(5,10));
        cols.add(List.of(4,1,7,1));
        cols.add(List.of(5,5,2));
        cols.add(List.of(1,1,1,6,1));

        cols.add(List.of(1,1,5,1));
        cols.add(List.of(1,4,5,1));
        cols.add(List.of(1,1,2,3,1));
        cols.add(List.of(1,2,5,2,1,1));
        cols.add(List.of(2,2,4,1));

        cols.add(List.of(1,1,1,1,3,1));
        cols.add(List.of(1,2,1,1,3,1));
        cols.add(List.of(6,2,2,2,1));
        cols.add(List.of(3,1,5,4,1,1));
        cols.add(List.of(3,4,5,1));

        cols.add(List.of(3,4,3,1));
        cols.add(List.of(5,1,5,3,1));
        cols.add(List.of(12,1,4));
        cols.add(List.of(2,2,2,1));
        cols.add(List.of(1,2));

        cols.add(List.of(1,8));
        cols.add(List.of(2,1,2,1));
        cols.add(List.of(4,1,4));
        cols.add(List.of(4,1,2,1));
        cols.add(List.of(2,1,2,1));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle16ProfessionalLevel2() {
        var width = 20;
        var height = 30;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(5));
        rows.add(List.of(8));
        rows.add(List.of(9));
        rows.add(List.of(10));
        rows.add(List.of(4,5));

        rows.add(List.of(3,2,3));
        rows.add(List.of(1,3,2));
        rows.add(List.of(4,2));
        rows.add(List.of(5,2,4));
        rows.add(List.of(4,1,3));

        rows.add(List.of(1,1,7));
        rows.add(List.of(5,5,3));
        rows.add(List.of(2,3,6,4));
        rows.add(List.of(1,4,5,1,5));
        rows.add(List.of(1,3,5,5,2));

        rows.add(List.of(4,6,6,1));
        rows.add(List.of(4,13,1));
        rows.add(List.of(3,4,5,4));
        rows.add(List.of(1,1,3,5,1,1));
        rows.add(List.of(2,4,3,2));

        rows.add(List.of(1,1,5,1,1));
        rows.add(List.of(1,5,1,1));
        rows.add(List.of(1,2,2,1,1));
        rows.add(List.of(2,1,2,2,2,2));
        rows.add(List.of(1,1,1,3,1));

        rows.add(List.of(1,1,1,1,1,2,1));
        rows.add(List.of(2,2,1,1,1,2));
        rows.add(List.of(1,1,1,1,2,1,1));
        rows.add(List.of(2,2,1,1,2,1));
        rows.add(List.of(1,1,1,1,1,2,2));

        cols.add(List.of(8,2));
        cols.add(List.of(3,2,3,2,3));
        cols.add(List.of(3,1,6,4));
        cols.add(List.of(7,6,5,2));
        cols.add(List.of(14,3,3));

        cols.add(List.of(7,2,3,7,4));
        cols.add(List.of(5,9));
        cols.add(List.of(4,1,1,5,10));
        cols.add(List.of(6,1,5,4,1));
        cols.add(List.of(5,1,11,1,1,1));

        cols.add(List.of(4,1,9,2));
        cols.add(List.of(5,1,4,3,1,3));
        cols.add(List.of(11,5,4));
        cols.add(List.of(4,8,5,1));
        cols.add(List.of(3,3,3,2));

        cols.add(List.of(2,6,4));
        cols.add(List.of(11));
        cols.add(List.of(4,3,5));
        cols.add(List.of(4,2,4,1));
        cols.add(List.of(4,4));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle17ProfessionalLevel2() {
        var width = 25;
        var height = 35;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of(3));
        rows.add(List.of(6));
        rows.add(List.of(9));
        rows.add(List.of(5,2));
        rows.add(List.of(3,2));

        rows.add(List.of(2,1,2));
        rows.add(List.of(3,2,2));
        rows.add(List.of(6,2,1));
        rows.add(List.of(5,1,2));
        rows.add(List.of(4,1,1));

        rows.add(List.of(3,3));
        rows.add(List.of(2,1,2));
        rows.add(List.of(2,1,2,1,1));
        rows.add(List.of(6,2,1,1));
        rows.add(List.of(3,1,1,4));

        rows.add(List.of(3,1,1,2));
        rows.add(List.of(4,1,1,1,2));
        rows.add(List.of(3,2,1,1));
        rows.add(List.of(4,2,2,1,1));
        rows.add(List.of(6,1,1,1,2,1));

        rows.add(List.of(3,2,1,1,1,2,2,2));
        rows.add(List.of(2,1,2,9));
        rows.add(List.of(2,1,2,1,2));
        rows.add(List.of(2,3,3));
        rows.add(List.of(3,1,2,2,2));

        rows.add(List.of(4,2,3,1,2));
        rows.add(List.of(6,3,2,3));
        rows.add(List.of(11,1,3));
        rows.add(List.of(7,7));
        rows.add(List.of(1,6));

        rows.add(List.of(4,5));
        rows.add(List.of(7,5));
        rows.add(List.of(12,5));
        rows.add(List.of(7,6));
        rows.add(List.of(3,8));

        cols.add(List.of(5,4));
        cols.add(List.of(8,3));
        cols.add(List.of(3,4,3));
        cols.add(List.of(5,4,3));
        cols.add(List.of(5,3,2));

        cols.add(List.of(6,3,3));
        cols.add(List.of(7,2,2,3));
        cols.add(List.of(6,2,2,2));
        cols.add(List.of(3,1,2,2,2,3));
        cols.add(List.of(3,1,4,3));

        cols.add(List.of(7,2,4,2,3));
        cols.add(List.of(7,1,4,4,2));
        cols.add(List.of(3,3,2,2,2,1));
        cols.add(List.of(3,1,3,6,7));
        cols.add(List.of(3,1,5,1,9));

        cols.add(List.of(3,6,7));
        cols.add(List.of(3,4,3,2,11));
        cols.add(List.of(3,3,3,2,9));
        cols.add(List.of(2,1,10,2));
        cols.add(List.of(2,3,1,1,3,1));

        cols.add(List.of(6,2,1,4));
        cols.add(List.of(3,4,4));
        cols.add(List.of(2,2,1));
        cols.add(List.of(2,2));
        cols.add(List.of(5));
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getTestPuzzle_template() {
        var width = 25;
        var height = 25;
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());

        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());

        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());

        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());

        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());
        rows.add(List.of());

        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());

        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());

        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());

        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());

        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        cols.add(List.of());
        var puzzle = new Puzzle(width, height, rows, cols);
        return puzzle;
    }

    private Puzzle getPuzzleFromInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("width: ");
        var width = Integer.parseInt(br.readLine());
        System.out.print("height: ");
        var height = Integer.parseInt(br.readLine());
        var rows = new ArrayList<List<Integer>>();
        var cols = new ArrayList<List<Integer>>();
        for (int i = 0; i < height; i++) {
            System.out.print("row " + (i + 1) + ": ");
            rows.add(parse(br.readLine()));
        }
        return new Puzzle(width, height, rows, cols);
    }

    private static List<Integer> parse(String line) {
        return Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

    }
}