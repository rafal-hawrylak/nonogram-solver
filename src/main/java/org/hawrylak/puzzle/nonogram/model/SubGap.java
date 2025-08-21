package org.hawrylak.puzzle.nonogram.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SubGap {

    public final int start;
    public final int end;
    public final int length;
}
