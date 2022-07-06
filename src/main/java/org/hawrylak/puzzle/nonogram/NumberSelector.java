package org.hawrylak.puzzle.nonogram;

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
}
