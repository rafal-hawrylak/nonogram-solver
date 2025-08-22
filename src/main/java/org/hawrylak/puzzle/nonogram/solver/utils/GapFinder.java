package org.hawrylak.puzzle.nonogram.solver.utils;

import org.hawrylak.puzzle.nonogram.model.*;

import java.util.*;

import static org.hawrylak.puzzle.nonogram.model.Gap.NO_FULL;

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
                            var assignedNumber = findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1);
                            gaps.add(new Gap(rowOrCol, start, start + length - 1, length, assignedNumber, new ArrayList<>(subGaps)));
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
                            var assignedNumber = findAssignedNumber(rowOrCol.numbersToFind, start, start + length - 1);
                            gaps.add(new Gap(rowOrCol, start, start + length - 1, length, assignedNumber, new ArrayList<>(subGaps)));
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
        return getPossibleGapAtPosition(gaps, start, end).get();
    }

    public Optional<Gap> getPossibleGapAtPosition(List<Gap> gaps, int start, int end) {
        return gaps.stream()
            .filter(g -> g.start <= start)
            .filter(g -> g.end >= end)
            .findAny();
    }

    public Optional<Gap> previous(List<Gap> gaps, Gap gap) {
        return Utils.previous(gaps, gap);
    }

    public Optional<SubGap> previous(List<SubGap> gaps, SubGap gap) {
        return Utils.previous(gaps, gap);
    }

    public Optional<Gap> next(List<Gap> gaps, Gap gap) {
        return Utils.next(gaps, gap);
    }

    public Optional<SubGap> next(List<SubGap> gaps, SubGap gap) {
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
        return new Gap(rowOrCol, start, start + maxCount - 1, maxCount);
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
            : Optional.of(withoutAssignedNumber.getLast());
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
        var sizeAfterMerging = getSizeAfterMerging(subGap, nextSubGap);
        return sizeAfterMerging <= number;
    }

    public int getSizeAfterMerging(SubGap subGap, SubGap nextSubGap) {
        return nextSubGap.end - subGap.start + 1;
    }

    public boolean numberFitsBetweenSubGaps(int number, SubGap subGap, SubGap nextSubGap) {
        var spaceBetween = nextSubGap.start - subGap.end - 1;
        return spaceBetween >= number + 2; // + 2 for both "x" at ends
    }

    public Optional<SubGap> getSubGapAtPosition(List<SubGap> filledSubGaps, boolean horizontal, int c, int r) {
        var position = horizontal ? c : r;
        return filledSubGaps.stream().filter(s -> s.start <= position).filter(s -> s.end >= position).findFirst();
    }

    public Gap getGapOfSubGap(List<Gap> gaps, SubGap subGap) {
        return getGapAtPosition(gaps, subGap.start, subGap.end);
    }

    public List<SubGap> getBiggerFirst(List<SubGap> subGaps) {
        return subGaps.stream()
                .sorted(Comparator.comparingInt(s -> ((SubGap)s).length).reversed())
                .toList();
    }

    public List<SubGap> biggestSubGap(List<SubGap> subGaps) {
        var biggerFirst = getBiggerFirst(subGaps);
        if (biggerFirst.isEmpty()) {
            return Collections.emptyList();
        }
        var biggest = biggerFirst.get(0).length;
        return biggerFirst.stream().filter(n -> n.length == biggest).toList();
    }

    public List<SubGap> gapsEndingAfterSubGap(SubGap subGap, List<Gap> gaps) {
        var lastGap = getGapAtPosition(gaps, subGap.end, subGap.end);
        var previousGaps = allPrevious(gaps, lastGap);
        var artificialGap = new Gap(lastGap.rowOrCol, lastGap.start, subGap.end, subGap.end - lastGap.start + 1);
        var result = new ArrayList<SubGap>();
        result.addAll(previousGaps);
        result.add(artificialGap);
        return result;
    }

    public List<SubGap> gapsStartingBeforeSubGap(SubGap subGap, List<Gap> gaps) {
        var firstGap = getGapAtPosition(gaps, subGap.start, subGap.start);
        var nextGaps = allNext(gaps, firstGap);
        var artificialGap = new Gap(firstGap.rowOrCol, subGap.start, firstGap.end, firstGap.end - subGap.start + 1);
        var result = new ArrayList<SubGap>();
        result.add(artificialGap);
        result.addAll(nextGaps);
        return result;
    }

    public List<SubGap> sumGaps(List<SubGap> subGaps1, List<SubGap> subGaps2) {
        var distinctMiddleSubGaps = new HashSet<SubGap>();
        if (subGaps1.size() >= 3) {
            distinctMiddleSubGaps.addAll(subGaps1.subList(1, subGaps1.size() - 1));
        }
        if (subGaps2.size() >= 3) {
            distinctMiddleSubGaps.addAll(subGaps2.subList(1, subGaps2.size() - 1));
        }
        if (!distinctMiddleSubGaps.stream().anyMatch(s -> s.start == subGaps1.getFirst().start && s.end == subGaps1.getFirst().end)) {
            distinctMiddleSubGaps.add(subGaps1.getFirst());
        }
        if (!distinctMiddleSubGaps.stream().anyMatch(s -> s.start == subGaps2.getLast().start && s.end == subGaps2.getLast().end)) {
            distinctMiddleSubGaps.add(subGaps2.getLast());
        }
        return distinctMiddleSubGaps.stream().sorted(Comparator.comparingInt(s -> s.start)).toList();
    }
}
