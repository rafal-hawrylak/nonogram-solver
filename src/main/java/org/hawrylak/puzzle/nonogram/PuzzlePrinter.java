package org.hawrylak.puzzle.nonogram;

import java.util.Optional;

public class PuzzlePrinter {

    private static final int COL_WIDTH = 3;
    private static final int INDEX_WIDTH = 2;

    public String print(Puzzle puzzle, Optional<ChangedInIteration> changes) {
        StringBuilder sb = new StringBuilder();
        // column indexes
        sb.append("   ");
        for (int c = 0; c < puzzle.width; c++) {
            sb.append(StringUtils.pad("" + c, ' ', COL_WIDTH));
        }
        sb.append("\n");
        // rows
        var rows = puzzle.rowsOrCols.stream().filter(rc -> rc.horizontal).toList();
        for (int r = 0; r < puzzle.height; r++) {
            sb.append(StringUtils.pad("" + r, ' ', INDEX_WIDTH));
            sb.append("|");
            for (int c = 0; c < puzzle.width; c++) {
                sb.append(getField(puzzle, r, c, changes));
            }
            sb.append("|");
            for (NumberToFind number : rows.get(r).numbersToFind) {
                appendNumber(sb, number.found, " " + number.number);
            }
            sb.append("\n");
        }
        // cols
        var cols = puzzle.rowsOrCols.stream().filter(rc -> !rc.horizontal).toList();
        if (!cols.isEmpty()) {
            var maxHeight = cols.stream().map(c -> c.numbersToFind.size()).max(Integer::compareTo)
                .get();
            for (int h = 1; h <= maxHeight; h++) {
                sb.append(StringUtils.pad("", ' ', COL_WIDTH));
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
        }
        return sb.toString();
    }

    private String getField(Puzzle puzzle, int r, int c, Optional<ChangedInIteration> changes) {
        var sb = new StringBuilder();
        var color = false;
        if (changes.isPresent()) {
            if (changes.get().changedFields[c][r]) {
                color = true;
                sb.append(puzzle.fields[c][r].equals(FieldState.EMPTY) ? Colors.ANSI_RED
                    : Colors.ANSI_GREEN);
            }
        }
        String value = switch (puzzle.fields[c][r]) {
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
