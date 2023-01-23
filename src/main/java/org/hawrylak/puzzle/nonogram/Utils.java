package org.hawrylak.puzzle.nonogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hawrylak.puzzle.nonogram.model.RowOrCol;

public class Utils {

    public static <T> List<T> reverse(List<T> list) {
        var newList = new ArrayList<>(list);
        Collections.reverse(newList);
        return newList;
    }

    public static <T> Optional<T> previous(List<T> elements, T element) {
        Optional<T> previous = Optional.empty();
        for (T current : elements) {
            if (current.equals(element)) {
                return previous;
            }
            previous = Optional.of(current);
        }
        return Optional.empty();
    }

    public static <T> Optional<T> next(List<T> elements, T element) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(element)) {
                if (i == elements.size() - 1) {
                    return Optional.empty();
                } else {
                    return Optional.of(elements.get(i + 1));
                }
            }
        }
        return Optional.empty();
    }

    public static <T> List<T> allPrevious(List<T> elements, T element) {
        var found = new ArrayList<T>();
        for (T current : elements) {
            if (current.equals(element)) {
                break;
            }
            found.add(current);
        }
        return found;
    }

    public static <T> List<T> allNext(List<T> elements, T element) {
        var found = new ArrayList<T>();
        var startAdding = false;
        for (T current : elements) {
            if (current.equals(element)) {
                startAdding = true;
            } else if (startAdding) {
                found.add(current);
            }
        }
        return found;
    }

    public static <T> Optional<T> getLast(List<T> elements) {
        return elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(elements.size() - 1));
    }

    public static <T> Optional<T> getFirst(List<T> elements) {
        return elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(0));
    }

    public static <T> List<T> mergeLists(List<T> elements, T element) {
        List<T> newList = new ArrayList<>(elements);
        newList.add(element);
        return newList;
    }

    public static <T> List<T> mergeLists(T element, List<T> elements) {
        List<T> newList = new ArrayList<>();
        newList.add(element);
        newList.addAll(elements);
        return newList;
    }

    public static int getStart(RowOrCol rowOrCol, int number, int c, int r, boolean startingFrom) {
        if (startingFrom) {
            return rowOrCol.horizontal ? c : r;
        } else {
            return rowOrCol.horizontal ? c - number + 1 : r - number + 1;
        }
    }
}
