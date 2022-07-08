package org.hawrylak.puzzle.nonogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    @SneakyThrows
    private void run() {
        System.out.println("Hello Nonogram Solver!");
        Puzzle puzzle = getTestPuzzle6();
        System.out.println(puzzle);

        boolean solved = new PuzzleSolver().solve(puzzle);

        System.out.println("solved = " + solved);
        System.out.println(puzzle);
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