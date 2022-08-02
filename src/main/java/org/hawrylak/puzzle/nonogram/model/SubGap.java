package org.hawrylak.puzzle.nonogram.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class SubGap {

    public final int start;
    public final int end;
    public final int length;
}
