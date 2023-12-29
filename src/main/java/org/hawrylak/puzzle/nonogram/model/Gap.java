package org.hawrylak.puzzle.nonogram.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.solver.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"rowOrCol"})
@EqualsAndHashCode
public class Gap {

    public static final int NO_FULL = -1;
    public final RowOrCol rowOrCol;
    public final int start;
    public final int end;
    public final int length;
    public Optional<NumberToFind> assignedNumber = Optional.empty();
    public List<SubGap> filledSubGaps = new ArrayList<>();

    public Gap(RowOrCol rowOrCol, int start, int end, int length, Optional<NumberToFind> assignedNumber) {
        this(rowOrCol, start, end, length);
        this.assignedNumber = assignedNumber;
    }

    public Optional<SubGap> getFirstSubGap() {
        return Utils.getFirst(filledSubGaps);
    }

    public Optional<SubGap> getLastSubGap() {
        return Utils.getLast(filledSubGaps);
    }
}
