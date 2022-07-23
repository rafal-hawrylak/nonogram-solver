package org.hawrylak.puzzle.nonogram;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class SubGap {
    final int start;
    final int end;
    final int length;
}
