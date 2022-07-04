package org.hawrylak.puzzle.nonogram;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class NumberToFind {
    final int number;
    boolean found = false;
}
