package org.hawrylak.puzzle.nonogram.model;

import lombok.EqualsAndHashCode;
import org.hawrylak.puzzle.nonogram.utils.ChangedInIteration;
import org.hawrylak.puzzle.nonogram.utils.PuzzlePrinter;
import org.hawrylak.puzzle.nonogram.utils.PuzzleStatisticsCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Puzzle {

    public final int width;
    public final int height;

    @EqualsAndHashCode.Exclude
    public final List<RowOrCol> rowsOrCols = new ArrayList<>();

    public FieldState[][] fields;

    private final PuzzlePrinter puzzlePrinter = new PuzzlePrinter();
    private final PuzzleStatisticsCalculator puzzleStatisticsCalculator = new PuzzleStatisticsCalculator();

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
        sb.append(completion());
        sb.append(puzzlePrinter.print(this, Optional.of(changes)));
        return sb.toString();
    }

    private String completion() {
        var statistics = puzzleStatisticsCalculator.calculate(this);
        int completed = statistics.numberOfEmptyFields() + statistics.numberOfFullFields();
        int total = statistics.numberOfFields();
        int completion = (100 * completed) / total;
        return String.format("completion %s%% (%s / %s)\n", completion, completed, total);
    }

    public String compact() {
        return puzzlePrinter.printCompact(this);
    }

    public Iterable<? extends RowOrCol> getUnsolvedRowsOrCols() {
        return this.rowsOrCols.stream().filter(roc -> !roc.solved).collect(Collectors.toUnmodifiableList());
    }
}
