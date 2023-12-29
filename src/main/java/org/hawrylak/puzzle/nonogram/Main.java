package org.hawrylak.puzzle.nonogram;

import org.hawrylak.puzzle.nonogram.model.Puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

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
        return new Puzzle(width, height, rows, cols);
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