package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.List;

public class PuzzleStringConverter {

    public Puzzle fromString(String puzzleCase) {
        return fromString(puzzleCase, List.of());
    }

    public Puzzle fromString(String input, List<Integer> numbersToFind) {
        if (input.contains("\n")) {
            throw new IllegalArgumentException("multiline string not supported yet");
        }
        var width = input.length();
        var height = 1;
        List<Integer> row = new ArrayList<>();

        FieldState[][] fields = new FieldState[width][height];
        var currentNumber = 0;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c <= width; c++) {
                var currentField = FieldState.OUTSIDE;
                if (c < width) {
                    fields[c][r] = char2Field("" + input.charAt(c));
                    currentField = fields[c][r];
                }
                if (c == 0) {
                    if (FieldState.FULL.equals(fields[c][r])) {
                        currentNumber = 1;
                    }
                } else {
                    switch (fields[c - 1][r]) {
                        case UNKNOWN, EMPTY -> {
                            if (FieldState.FULL.equals(currentField)) {
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
        Puzzle puzzle = new Puzzle(width, 1, rows, new ArrayList<>());
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
