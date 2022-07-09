package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Puzzle {

    final int width;
    final int height;

    final List<RowOrCol> rowsOrCols = new ArrayList<>();

    FieldState[][] fields;

    private final PuzzlePrinter puzzlePrinter = new PuzzlePrinter();

    public Puzzle(int width, int height, List<List<Integer>> rows, List<List<Integer>> cols) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < rows.size(); i++) {
            this.rowsOrCols.add(new RowOrCol(rows.get(i), i, true));
        }
        for (int i = 0; i < cols.size(); i++) {
            this.rowsOrCols.add(new RowOrCol(cols.get(i), i, false));
        }
        initFields();
    }

    private void initFields() {
        fields = new FieldState[width][height];
        for (int c = 0; c < width; c++) {
            for (int r = 0; r < height; r++) {
                fields[c][r] = FieldState.UNKNOWN;
            }
        }
    }

    @Override
    public String toString() {
        return puzzlePrinter.print(this, Optional.empty());
    }

    public String toString(ChangedInIteration changes) {
        return toString(changes, "");
    }

    public String toString(ChangedInIteration changes, String debugHeader) {
        var sb = new StringBuilder();
            if (!debugHeader.isEmpty()) {
                sb.append("DEBUG: " + debugHeader + "\n");
            }
            sb.append(puzzlePrinter.print(this, Optional.of(changes)));
        return sb.toString();
    }
}
