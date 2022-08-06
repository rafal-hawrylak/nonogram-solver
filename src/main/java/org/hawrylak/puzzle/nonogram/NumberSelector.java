package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.hawrylak.puzzle.nonogram.model.FieldState;
import org.hawrylak.puzzle.nonogram.model.Gap;
import org.hawrylak.puzzle.nonogram.model.NumberToFind;
import org.hawrylak.puzzle.nonogram.model.Puzzle;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class NumberSelector {

    public Optional<NumberToFind> getNext(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        return Utils.next(numbersToFind, numberToFind);
    }

    public Optional<NumberToFind> getPrevious(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        return Utils.previous(numbersToFind, numberToFind);
    }

    public List<NumberToFind> allPrevious(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        return Utils.allPrevious(numbersToFind, numberToFind);
    }

    public List<NumberToFind> allNext(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        return Utils.allNext(numbersToFind, numberToFind);
    }

    public Optional<NumberToFind> getLast(List<NumberToFind> numbersToFind) {
        return Utils.getLast(numbersToFind);
    }

    public Optional<NumberToFind> getFirst(List<NumberToFind> numbersToFind) {
        return Utils.getFirst(numbersToFind);
    }

    public Optional<NumberToFind> getForPositionAssumingAllFullInTheRowOrColFilled(RowOrCol rowOrCol, Puzzle puzzle, int start, int end) {
        var k = rowOrCol.number;
        var limit = rowOrCol.horizontal ? puzzle.width : puzzle.height;
        var previousField = FieldState.OUTSIDE;
        var numberIndex = -1;
        for (int i = 0; i <= limit; i++) {
            var field = i == limit ? FieldState.OUTSIDE : (rowOrCol.horizontal ? puzzle.fields[i][k] : puzzle.fields[k][i]);
            switch (previousField) {
                case OUTSIDE, UNKNOWN, EMPTY -> {
                    if (FieldState.FULL.equals(field)) {
                        numberIndex++;
                        if (i == start) {
                            var candidate = rowOrCol.numbersToFind.get(numberIndex);
                            if (i + candidate.number - 1 == end) {
                                return Optional.of(candidate);
                            } else {
                                return Optional.empty();
                            }
                        }
                    }
                }
            }
            previousField = field;
        }
        return Optional.empty();
    }

    public Optional<NumberToFind> getFirstNotFound(List<NumberToFind> numbers) {
        return numbers.stream().filter(n -> !n.found).findFirst();
    }

    public Optional<NumberToFind> getLastNotFound(List<NumberToFind> numbers) {
        var copy = new ArrayList<>(numbers);
        Collections.reverse(copy);
        return copy.stream().filter(n -> !n.found).findFirst();
    }

    public List<NumberToFind> getBiggerFirstOnlyNotFound(List<NumberToFind> numbers) {
        return numbers.stream()
            .filter(n -> !n.found)
            .sorted(Comparator.comparingInt(NumberToFind::getNumber).reversed())
            .toList();
    }

    public List<NumberToFind> getBiggestNotFound(List<NumberToFind> numbers) {
        var biggerFirstOnlyNotFound = getBiggerFirstOnlyNotFound(numbers);
        if (biggerFirstOnlyNotFound.isEmpty()) {
            return Collections.emptyList();
        }
        var biggest = biggerFirstOnlyNotFound.get(0).number;
        return biggerFirstOnlyNotFound.stream().filter(n -> n.number == biggest).toList();
    }

    public List<NumberToFind> getSecondBiggestNotFound(List<NumberToFind> numbers) {
        var biggestNotFound = getBiggestNotFound(numbers);
        if (biggestNotFound.isEmpty()) {
            return Collections.emptyList();
        }
        var biggerFirstOnlyNotFound = getBiggerFirstOnlyNotFound(numbers);
        if (biggestNotFound.size() == biggerFirstOnlyNotFound.size()) {
            return Collections.emptyList();
        }
        var secondBiggest = biggerFirstOnlyNotFound.get(biggestNotFound.size()).number;
        return biggerFirstOnlyNotFound.stream().filter(n -> n.number == secondBiggest).toList();
    }

    public List<NumberToFind> getNotFound(List<NumberToFind> numbers) {
        return numbers.stream().filter(n -> !n.found).toList();
    }

    public List<NumberToFind> getNumbersBetween(List<NumberToFind> numbers, Optional<NumberToFind> numberPrevious,
        Optional<NumberToFind> numberNext) {
        var start = numberPrevious.isPresent() ? numbers.indexOf(numberPrevious.get()) + 1 : 0;
        var end = numberNext.isPresent() ? numbers.indexOf(numberNext.get()) : numbers.size();
        return start <= end ? numbers.subList(start, end) : Collections.emptyList();
    }

    public List<NumberToFind> findNumbersPossibleToFitInGap(Gap gap, List<NumberToFind> numbers) {
        var numbersPossibleToFit = new ArrayList<NumberToFind>();
        var sumSoFar = 0;
        for (NumberToFind number : numbers) {
            if (sumSoFar + number.number <= gap.length) {
                numbersPossibleToFit.add(number);
                sumSoFar += number.number + 1;
            } else {
                break;
            }
        }
        return numbersPossibleToFit;
    }

    public int calculateGapDiff(Gap gap, List<NumberToFind> numbersSubList) {
        var numbersSum = numbersSubList.stream().map(n -> n.number).reduce(0, Integer::sum);
        var gapDiff = gap.length - numbersSum - numbersSubList.size() + 1;
        return gapDiff;
    }

    public List<NumberBeforeCurrentAndAfter> getAllPossibleSplitsAtNumber(List<NumberToFind> numbers, int number) {
        var allPossible = new ArrayList<NumberBeforeCurrentAndAfter>();
        var allNumberOccurrences = numbers.stream().filter(n -> n.number == number).toList();
        for (NumberToFind occurrence : allNumberOccurrences) {
            var allPrevious = allPrevious(numbers, occurrence);
            var allNext = allNext(numbers, occurrence);
            allPossible.add(new NumberBeforeCurrentAndAfter(allPrevious, occurrence, allNext));
        }
        return allPossible;
    }

    public List<NumberToFind> mergeLists(List<NumberToFind> numbers, NumberToFind number) {
        return Utils.mergeLists(numbers, number);
    }

    public List<NumberToFind> mergeLists(NumberToFind number, List<NumberToFind> numbers) {
        return Utils.mergeLists(number, numbers);
    }

    public int sum(List<NumberToFind> numbers) {
        return numbers.stream().mapToInt(n -> n.number).sum();
    }

    public record NumberBeforeCurrentAndAfter(List<NumberToFind> before, NumberToFind current, List<NumberToFind> after) {

    }
}