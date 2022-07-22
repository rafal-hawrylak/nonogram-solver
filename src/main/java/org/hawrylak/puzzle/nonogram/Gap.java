package org.hawrylak.puzzle.nonogram;

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
    final RowOrCol rowOrCol;
    final int start;
    final int end;
    final int length;
    final Optional<NumberToFind> assignedNumber;
    List<SubGap> filledSubGaps;
}
