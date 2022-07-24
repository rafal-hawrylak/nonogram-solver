package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

public class PuzzleStringConverter {

    public Puzzle fromString(String puzzleCase) {
        return fromString(puzzleCase, List.of(), true);
    }

    public Puzzle fromString(String input, List<Integer> numbersToFind, boolean withCols) {
        if (input.contains("\n")) {
            throw new IllegalArgumentException("multiline string not supported yet");
        }
        var width = input.length();
        var height = 1;
        List<Integer> row = new ArrayList<>();
        List<List<Integer>> cols = new ArrayList<>();

        FieldState[][] fields = new FieldState[width][height];
        var currentNumber = 0;
        for (int c = 0; c <= width; c++) {
            for (int r = 0; r < height; r++) {
                var currentField = FieldState.OUTSIDE;
                if (c < width) {
                    fields[c][r] = char2Field("" + input.charAt(c));
                    currentField = fields[c][r];
                    // support only for N x 1 puzzles
                    cols.add(List.of());
                }
                var isFull = FieldState.FULL.equals(currentField);
                if (c == 0) {
                    if (isFull) {
                        currentNumber = 1;
                    }
                } else {
                    switch (fields[c - 1][r]) {
                        case UNKNOWN, EMPTY -> {
                            if (isFull) {
                                currentNumber = 1;
                            }
                        }
                        case FULL -> {
                            switch (currentField) {
                                case FULL -> currentNumber++;
                                case UNKNOWN, EMPTY, OUTSIDE -> {
                                    row.add(currentNumber);
                                    currentNumber = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        var rows = numbersToFind.isEmpty() ? List.of(row) : List.of(numbersToFind);
        Puzzle puzzle = new Puzzle(width, 1, rows, withCols ? cols : Collections.emptyList());
        puzzle.fields = fields;
        return puzzle;
    }

    public String fromPuzzle(Puzzle puzzle) {
        var sb = new StringBuilder();
        for (int r = 0; r < puzzle.height; r++) {
            if (r > 0) {
                sb.append("\n");
            }
            for (int c = 0; c < puzzle.width; c++) {
                sb.append(field2Char(puzzle.fields[c][r]));
            }
        }
        return sb.toString();
    }

    private FieldState char2Field(String c) {
        return switch (c) {
            case "." -> FieldState.UNKNOWN;
            case "x" -> FieldState.EMPTY;
            case "■" -> FieldState.FULL;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private String field2Char(FieldState field) {
        return switch (field) {
            case UNKNOWN -> ".";
            case EMPTY -> "x";
            case FULL -> "■";
            default -> throw new IllegalStateException("Unexpected value: " + field);
        };
    }
}
