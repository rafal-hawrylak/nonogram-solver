package org.hawrylak.puzzle.nonogram.model.statistics;

public record PuzzleStatistics (int numberOfFields, int numberOfUnknownFields, int numberOfEmptyFields, int numberOfFullFields) {

    @Override
    public String toString() {
        return "PuzzleStatistics{" +
                "total=" + numberOfFields +
                ", empty=" + numberOfEmptyFields +
                ", full=" + numberOfFullFields +
                ", unknown=" + numberOfUnknownFields +
                '}';
    }
}
