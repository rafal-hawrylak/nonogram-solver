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

    public Puzzle(int width, int height, ArrayList<List<Integer>> rowsOrCols,
        ArrayList<List<Integer>> cols) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < rowsOrCols.size(); i++) {
            this.rowsOrCols.add(new RowOrCol(rowsOrCols.get(i), i, true));
        }
        for (int i = 0; i < cols.size(); i++) {
            this.rowsOrCols.add(new RowOrCol(cols.get(i), i, false));
        }
        initFields();
    }

    private void initFields() {
        fields = new FieldState[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = FieldState.UNKNOWN;
            }
        }
    }

    @Override
    public String toString() {
        return puzzlePrinter.print(this, Optional.empty());
    }

    public String toString(ChangedInIteration changes) {
        return puzzlePrinter.print(this, Optional.of(changes));
    }

}
