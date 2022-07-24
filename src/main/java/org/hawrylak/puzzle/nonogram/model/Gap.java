package org.hawrylak.puzzle.nonogram.model;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Gap {

    public static final int NO_FULL = -1;
    public final RowOrCol rowOrCol;
    public final int start;
    public final int end;
    public final int length;
    public final Optional<NumberToFind> assignedNumber;
    public List<SubGap> filledSubGaps;
}
