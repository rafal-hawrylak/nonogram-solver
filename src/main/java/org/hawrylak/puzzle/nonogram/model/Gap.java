package org.hawrylak.puzzle.nonogram.model;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hawrylak.puzzle.nonogram.Utils;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Gap {

    public static final int NO_FULL = -1;
    public final RowOrCol rowOrCol;
    public final int start;
    public final int end;
    public final int length;
    //TODO remove final - all the object creations must be fixed
    public final Optional<NumberToFind> assignedNumber;
    public List<SubGap> filledSubGaps;

    public Optional<SubGap> getFirstSubGap() {
        return Utils.getFirst(filledSubGaps);
    }

    public Optional<SubGap> getLastSubGap() {
        return Utils.getLast(filledSubGaps);
    }
}
