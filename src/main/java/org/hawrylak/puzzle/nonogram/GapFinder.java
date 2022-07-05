package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GapFinder {

    List<Gap> find(Puzzle puzzle, RowOrCol rowOrCol) {
        int k = rowOrCol.number;
        var maxSize = rowOrCol.horizontal ? puzzle.width : puzzle.height;
        List<Gap> gaps = new ArrayList<>();
        var start = 0;
        var length = 0;
        var startFilling = 0;
        var lengthFilling = 0;
        var previousField = FieldState.OUTSIDE;
        for (int i = 0; i <= maxSize; i++) {
            var field = getField(puzzle, rowOrCol, k, i);
            switch (previousField) {
                case OUTSIDE -> {
                    switch (field) {
                        case UNKNOWN -> length++;
                        case FULL -> {
                            length++;
                            startFilling = i;
                            lengthFilling = 1;
                        }
                    }
                }
                case UNKNOWN -> {
                    switch (field) {
                        case UNKNOWN -> length++;
                        case EMPTY, OUTSIDE -> gaps.add(new Gap(rowOrCol, start, start + length - 1, length,
                            findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1)));
                        case FULL -> {
                            length++;
                            startFilling = i;
                            lengthFilling = 1;
                        }
                    }
                }
                case EMPTY -> {
                    switch (field) {
                        case UNKNOWN -> {
                            start = i;
                            length = 1;
                        }
                        case FULL -> {
                            start = i;
                            length = 1;
                            startFilling = i;
                            lengthFilling = 1;
                        }
                    }
                }
                case FULL -> {
                    switch (field) {
                        case OUTSIDE, EMPTY -> gaps.add(new Gap(rowOrCol, start, start + length - 1, length,
                            findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1)));
                        case UNKNOWN -> {
                            // TODO gaps in gaps
                            length++;
                        }
                        case FULL -> {
                            length++;
                            lengthFilling++;
                        }
                    }
                }
            }
            previousField = field;
        }
        return gaps;
    }

    private FieldState getField(Puzzle puzzle, RowOrCol rowOrCol, int k, int i) {
        if (rowOrCol.horizontal) {
            return i == puzzle.width ? FieldState.OUTSIDE : puzzle.fields[i][k];
        } else {
            return i == puzzle.height ? FieldState.OUTSIDE : puzzle.fields[k][i];
        }
    }

    private Optional<NumberToFind> findAssignedNumber(List<NumberToFind> numbersToFind, int start, int end) {
        return numbersToFind.stream()
            .filter(n -> n.found)
            .filter(n -> n.foundStart == start)
            .filter(n -> n.fountEnd == end)
            .findAny();
    }

    public Gap getGapAtPosition(List<Gap> gaps, int i, int j) {
        return gaps.stream()
            .filter(g -> g.start <= i)
            .filter(g -> g.end >= j)
            .findAny().get();
    }

    public Optional<Gap> previousGap(List<Gap> gaps, Gap gap) {
        Optional<Gap> previousGap = Optional.empty();
        for (Gap currentGap : gaps) {
            if (currentGap.equals(gap)) {
                return previousGap;
            }
            previousGap = Optional.of(currentGap);
        }
        return Optional.empty();
    }
}
