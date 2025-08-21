package org.hawrylak.puzzle.nonogram.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ToString(exclude = {"rowOrCol"}, callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Gap extends SubGap {

    public static final int NO_FULL = -1;
    public final RowOrCol rowOrCol;
    public Optional<NumberToFind> assignedNumber = Optional.empty();
    public List<SubGap> filledSubGaps = new ArrayList<>();

    public Gap(RowOrCol rowOrCol, int start, int end, int length) {
        super(start, end, length);
        this.rowOrCol = rowOrCol;
    }

    public Gap(RowOrCol rowOrCol, int start, int end, int length, Optional<NumberToFind> assignedNumber) {
        this(rowOrCol, start, end, length);
        this.assignedNumber = assignedNumber;
    }

    public Gap(RowOrCol rowOrCol, int start, int end, int length, Optional<NumberToFind> assignedNumber, ArrayList<SubGap> subGaps) {
        this(rowOrCol, start, end, length, assignedNumber);
        this.filledSubGaps = subGaps;
    }

    public static List<SubGap> toSubGaps(List<Gap> gaps) {
        return gaps.stream().map(gap -> (SubGap)gap).toList();
    }
}
