package org.hawrylak.puzzle.nonogram;

import static org.hawrylak.puzzle.nonogram.model.Gap.NO_FULL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;
import org.hawrylak.puzzle.nonogram.model.SubGap;

public class GapFinder {

    public List<Gap> find(Puzzle puzzle, RowOrCol rowOrCol) {
        int k = rowOrCol.number;
        var maxSize = rowOrCol.horizontal ? puzzle.width : puzzle.height;
        List<Gap> gaps = new ArrayList<>();
        var start = 0;
        var length = 0;
        var firstFull = NO_FULL;
        var lastFull = NO_FULL;
        var previousField = FieldState.OUTSIDE;
        var subGaps = new ArrayList<SubGap>();
        for (int i = 0; i <= maxSize; i++) {
            var field = getField(puzzle, rowOrCol, i, k);
            switch (previousField) {
                case OUTSIDE -> {
                    switch (field) {
                        case UNKNOWN -> length++;
                        case FULL -> {
                            length++;
                            firstFull = i;
                            lastFull = i;
                        }
                    }
                }
                case UNKNOWN -> {
                    switch (field) {
                        case UNKNOWN -> length++;
                        case EMPTY, OUTSIDE -> {
                            if (firstFull != NO_FULL) {
                                subGaps.add(new SubGap(firstFull, lastFull, lastFull - firstFull + 1));
                            }
                            gaps.add(new Gap(rowOrCol, start, start + length - 1, length,
                                findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1), new ArrayList<>(subGaps)));
                            firstFull = NO_FULL;
                            lastFull = NO_FULL;
                            subGaps.clear();
                        }
                        case FULL -> {
                            length++;
                            firstFull = i;
                            lastFull = i;
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
                            firstFull = i;
                            lastFull = i;
                        }
                    }
                }
                case FULL -> {
                    switch (field) {
                        case OUTSIDE, EMPTY -> {
                            if (firstFull != NO_FULL) {
                                subGaps.add(new SubGap(firstFull, lastFull, lastFull - firstFull + 1));
                            }
                            gaps.add(new Gap(rowOrCol, start, start + length - 1, length,
                                findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1), new ArrayList<>(subGaps)));
                            firstFull = NO_FULL;
                            lastFull = NO_FULL;
                            subGaps.clear();
                        }
                        case UNKNOWN -> {
                            if (firstFull != NO_FULL) {
                                subGaps.add(new SubGap(firstFull, lastFull, lastFull - firstFull + 1));
                            }
                            firstFull = NO_FULL;
                            lastFull = NO_FULL;
                            length++;
                        }
                        case FULL -> {
                            length++;
                            lastFull = i;
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
        return Utils.previous(gaps, gap);
    }

    public Optional<Gap> next(List<Gap> gaps, Gap gap) {
        return Utils.next(gaps, gap);
    }

    public List<Gap> allPrevious(List<Gap> gaps, Gap gap) {
        return Utils.allPrevious(gaps, gap);
    }

    public List<Gap> allNext(List<Gap> gaps, Gap gap) {
        return Utils.allNext(gaps, gap);
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

    public Optional<Gap> findFirstWithoutNumberAssigned(Puzzle puzzle, RowOrCol rowOrCol) {
        return find(puzzle, rowOrCol).stream().filter(g -> g.assignedNumber.isEmpty()).findFirst();
    }

    public Optional<Gap> findLastWithoutNumberAssigned(Puzzle puzzle, RowOrCol rowOrCol) {
        var withoutAssignedNumber = findWithoutAssignedNumber(puzzle, rowOrCol);
        return withoutAssignedNumber.isEmpty() ? Optional.empty()
            : Optional.of(withoutAssignedNumber.get(withoutAssignedNumber.size() - 1));
    }

    public List<SubGap> allSubGaps(List<Gap> gaps) {
        return gaps.stream().flatMap(g -> g.filledSubGaps.stream()).toList();
    }

    public List<Gap> findWithoutAssignedNumber(Puzzle puzzle, RowOrCol rowOrCol) {
        return find(puzzle, rowOrCol).stream().filter(g -> g.assignedNumber.isEmpty()).toList();
    }

    public Optional<Gap> refreshSubGaps(Gap gap, Puzzle puzzle, RowOrCol rowOrCol) {
        var gaps = find(puzzle, rowOrCol);
        return gaps.stream().filter(g -> g.start == gap.start && g.end == gap.end).findFirst();
    }

    public boolean areSubGapsMergeable(int number, SubGap subGap, SubGap nextSubGap) {
        var sizeAfterMerging = nextSubGap.end - subGap.start + 1;
        return sizeAfterMerging <= number;
    }

    public boolean numberFitsBetweenSubGaps(int number, SubGap subGap, SubGap nextSubGap) {
        var spaceBetween = nextSubGap.start - subGap.end - 1;
        return spaceBetween >= number + 2; // + 2 for both "x" at ends
    }
}
