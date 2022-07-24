package org.hawrylak.puzzle.nonogram;

import java.util.Optional;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class PuzzlePrinter {

    private static final int COL_WIDTH = 3;
    private static final int INDEX_WIDTH = 2;

    public String print(Puzzle puzzle, Optional<ChangedInIteration> changes) {
        return print(puzzle, changes, false);
    }

    public String print(Puzzle puzzle, Optional<ChangedInIteration> changes, boolean compact) {
        StringBuilder sb = new StringBuilder();
        if (!compact) {
            // column indexes
            sb.append("   ");
            for (int c = 0; c < puzzle.width; c++) {
                sb.append(StringUtils.pad("" + c, ' ', COL_WIDTH));
            }
            sb.append("\n");
        }
        // rows
        var rows = puzzle.rowsOrCols.stream().filter(rc -> rc.horizontal).toList();
        for (int r = 0; r < puzzle.height; r++) {
            if (!compact) {
                sb.append(StringUtils.pad("" + r, ' ', INDEX_WIDTH));
            }
            sb.append("|");
            for (int c = 0; c < puzzle.width; c++) {
                sb.append(getField(puzzle, r, c, changes, compact));
            }
            sb.append("|");
            if (!compact) {
                for (NumberToFind number : rows.get(r).numbersToFind) {
                    appendNumber(sb, number.found, " " + number.number);
                }
            }
            sb.append("\n");
        }
        if (!compact) {
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
        }
        return sb.toString();
    }

    public String printCompact(Puzzle puzzle) {
        return print(puzzle, Optional.empty(), true);
    }

    private String getField(Puzzle puzzle, int r, int c, Optional<ChangedInIteration> changes, boolean compact) {
        var sb = new StringBuilder();
        var color = false;
        if (compact) {
            color = true;
            sb.append(Colors.ANSI_BLACK);
        }
        else if (changes.isPresent()) {
            if (changes.get().changedFields[c][r]) {
                color = true;
                sb.append(puzzle.fields[c][r].equals(FieldState.EMPTY) ? Colors.ANSI_RED
                    : Colors.ANSI_GREEN);
            }
        }
        String value = compact ?
            switch (puzzle.fields[c][r]) {
                case UNKNOWN, OUTSIDE, EMPTY -> " ";
                case FULL -> "■";
            }
        :
            switch (puzzle.fields[c][r]) {
            case UNKNOWN, OUTSIDE -> ".";
            case EMPTY -> "x";
            case FULL -> "■";
        };
        int colWidth = compact ? 2 : COL_WIDTH;
        sb.append(StringUtils.pad(value, ' ', colWidth));
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
