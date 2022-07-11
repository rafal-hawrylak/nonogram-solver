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
            var field = getField(puzzle, rowOrCol, i, k);
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

    private FieldState getField(Puzzle puzzle, RowOrCol rowOrCol, int i, int j) {
        if (rowOrCol.horizontal) {
            return i == puzzle.width ? FieldState.OUTSIDE : puzzle.fields[i][j];
        } else {
            return i == puzzle.height ? FieldState.OUTSIDE : puzzle.fields[j][i];
        }
    }

    private Optional<NumberToFind> findAssignedNumber(List<NumberToFind> numbersToFind, int start, int end) {
        return numbersToFind.stream()
            .filter(n -> n.found)
            .filter(n -> n.foundStart == start)
            .filter(n -> n.foundEnd == end)
            .findAny();
    }

    public Gap getGapAtPosition(List<Gap> gaps, int start, int end) {
        return gaps.stream()
            .filter(g -> g.start <= start)
            .filter(g -> g.end >= end)
            .findAny().get();
    }

    public Optional<Gap> previous(List<Gap> gaps, Gap gap) {
        Optional<Gap> previousGap = Optional.empty();
        for (Gap currentGap : gaps) {
            if (currentGap.equals(gap)) {
                return previousGap;
            }
            previousGap = Optional.of(currentGap);
        }
        return Optional.empty();
    }

    public Optional<Gap> next(List<Gap> gaps, Gap gap) {
        for (int i = 0; i < gaps.size(); i++) {
            if (gaps.get(i).equals(gap)) {
                if (i == gaps.size() - 1) {
                    return Optional.empty();
                } else {
                    return Optional.of(gaps.get(i + 1));
                }
            }
        }
        return Optional.empty();
    }

    public Gap maxSubsequentCountOfFields(Puzzle puzzle, RowOrCol rowOrCol, Gap gap, FieldState state) {
        var maxCount = 0;
        var count = 0;
        var start = 0;
        for (int i = gap.start; i <= gap.end + 1; i++) {
            var field = i == gap.end + 1 ? FieldState.OUTSIDE :
                rowOrCol.horizontal ? puzzle.fields[i][rowOrCol.number] : puzzle.fields[rowOrCol.number][i];
            var previousField = i == gap.start ? FieldState.OUTSIDE :
                rowOrCol.horizontal ? puzzle.fields[i - 1][rowOrCol.number] : puzzle.fields[rowOrCol.number][i - 1];
            switch (previousField) {
                case OUTSIDE, UNKNOWN, EMPTY -> {
                    switch (field) {
                        case FULL -> count = 1;
                    }
                }
                case FULL -> {
                    switch (field) {
                        case FULL -> count++;
                        case OUTSIDE, UNKNOWN, EMPTY -> {
                            if (count > maxCount) {
                                maxCount = count;
                                start = i - maxCount;
                                count = 0;
                            }
                        }
                    }
                }
            }
        }
        return new Gap(rowOrCol, start, start + maxCount - 1, maxCount, Optional.empty());
    }

    public List<Gap> gapsBeforeNumber(List<Gap> gaps, NumberToFind numberToFind) {
        var foundGaps = new ArrayList<Gap>();
        for (Gap gap : gaps) {
            if (gap.assignedNumber.isPresent() && gap.assignedNumber.get().equals(numberToFind)) {
                break;
            }
            foundGaps.add(gap);
        }
        return foundGaps;
    }

    public List<Gap> gapsAfterNumber(List<Gap> gaps, NumberToFind numberToFind) {
        var foundGaps = new ArrayList<Gap>();
        var numberFound = false;
        for (Gap gap : gaps) {
            if (numberFound) {
                foundGaps.add(gap);
            }
            if (gap.assignedNumber.isPresent() && gap.assignedNumber.get().equals(numberToFind)) {
                numberFound = true;
            }
        }
        return foundGaps;
    }
}
