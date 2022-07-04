package org.hawrylak.puzzle.nonogram;

import java.util.Optional;

public class PuzzlePrinter {

    private static final int COL_WIDTH = 3;

    public String print(Puzzle puzzle, Optional<ChangedInIteration> changes) {
        StringBuilder sb = new StringBuilder();
        // rows
        var rows = puzzle.rowsOrCols.stream().filter(rc -> rc.horizontal).toList();
        for (int i = 0; i < puzzle.height; i++) {
            sb.append("|");
            for (int j = 0; j < puzzle.width; j++) {
                sb.append(getField(puzzle, i, j, changes));
            }
            sb.append("|");
            for (NumberToFind row : rows.get(i).numbersToFind) {
                appendNumber(sb, row.found, " " + row.number);
            }
            sb.append("\n");
        }
        // cols
        var cols = puzzle.rowsOrCols.stream().filter(rc -> !rc.horizontal).toList();
        var maxHeight = cols.stream().map(c -> c.numbersToFind.size()).max(Integer::compareTo)
            .get();
        for (int h = 1; h <= maxHeight; h++) {
            sb.append(" ");
            for (RowOrCol col : cols) {
                if (col.numbersToFind.size() >= h) {
                    appendNumber(sb, col.numbersToFind.get(h - 1).found,
                        StringUtils.pad("" + col.numbersToFind.get(h - 1).number, ' ', COL_WIDTH));
                } else {
                    sb.append(StringUtils.pad("", ' ', COL_WIDTH));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getField(Puzzle puzzle, int i, int j, Optional<ChangedInIteration> changes) {
        var sb = new StringBuilder();
        var color = false;
        if (changes.isPresent()) {
            if (changes.get().changedFields[j][i]) {
                color = true;
                sb.append(puzzle.fields[j][i].equals(FieldState.EMPTY) ? Colors.ANSI_RED
                    : Colors.ANSI_GREEN);
            }
        }
        String value = switch (puzzle.fields[j][i]) {
            case UNKNOWN, OUTSIDE -> ".";
            case EMPTY -> "x";
            case FULL -> "■";
        };
        sb.append(StringUtils.pad(value, ' ', COL_WIDTH));
        if (color) {
            sb.append(Colors.ANSI_RESET);
        }
        return sb.toString();
    }

    private void appendNumber(StringBuilder sb, boolean found, String number) {
        sb.append(found ? Colors.ANSI_GREEN : Colors.ANSI_YELLOW);
        sb.append(number);
        sb.append(Colors.ANSI_RESET);
    }
}
