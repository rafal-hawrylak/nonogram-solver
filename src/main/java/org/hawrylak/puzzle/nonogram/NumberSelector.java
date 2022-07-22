package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NumberSelector {

    public Optional<NumberToFind> getNext(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        for (int i = 0; i < numbersToFind.size(); i++) {
            if (numbersToFind.get(i).equals(numberToFind)) {
                if (i == numbersToFind.size() - 1) {
                    return Optional.empty();
                } else {
                    return Optional.of(numbersToFind.get(i + 1));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<NumberToFind> getPrevious(List<NumberToFind> numbersToFind, NumberToFind numberToFind) {
        for (int i = 0; i < numbersToFind.size(); i++) {
            if (numbersToFind.get(i).equals(numberToFind)) {
                if (i == 0) {
                    return Optional.empty();
                } else {
                    return Optional.of(numbersToFind.get(i - 1));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<NumberToFind> getLast(List<NumberToFind> numbersToFind) {
        return numbersToFind.isEmpty() ? Optional.empty() : Optional.of(numbersToFind.get(numbersToFind.size() - 1));
    }

    public Optional<NumberToFind> getFirst(List<NumberToFind> numbersToFind) {
        return numbersToFind.isEmpty() ? Optional.empty() : Optional.of(numbersToFind.get(0));
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
}
