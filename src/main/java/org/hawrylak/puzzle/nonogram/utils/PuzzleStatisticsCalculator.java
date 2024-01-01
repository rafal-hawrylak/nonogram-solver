package org.hawrylak.puzzle.nonogram.utils;

import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.statistics.PuzzleStatistics;

public class PuzzleStatisticsCalculator {

    public PuzzleStatistics calculate(Puzzle puzzle) {
        int numberOfFields = puzzle.width * puzzle.height;
        int numberOfUnknownFields = 0;
        int numberOfEmptyFields = 0;
        int numberOfFullFields = 0;
        for (int c = 0; c < puzzle.width; c++) {
            for (int r = 0; r < puzzle.height; r++) {
                if (puzzle.fields[c][r] == FieldState.UNKNOWN) {
                    numberOfUnknownFields++;
                } else if (puzzle.fields[c][r] == FieldState.EMPTY) {
                    numberOfEmptyFields++;
                } else if (puzzle.fields[c][r] == FieldState.FULL) {
                    numberOfFullFields++;
                }
            }
        }

        return new PuzzleStatistics(numberOfFields, numberOfUnknownFields, numberOfEmptyFields, numberOfFullFields);
    }

    public PuzzleStatistics diff(Puzzle previous, Puzzle current) {
        var forPrevious = calculate(previous);
        var forCurrent = calculate(current);
        int numberOfFields = forCurrent.numberOfFields();
        int numberOfUnknownFields = forCurrent.numberOfUnknownFields() - forPrevious.numberOfUnknownFields();
        int numberOfEmptyFields = forCurrent.numberOfEmptyFields() - forPrevious.numberOfEmptyFields();
        int numberOfFullFields = forCurrent.numberOfFullFields() - forPrevious.numberOfFullFields();

        return new PuzzleStatistics(numberOfFields, numberOfUnknownFields, numberOfEmptyFields, numberOfFullFields);
    }
}
