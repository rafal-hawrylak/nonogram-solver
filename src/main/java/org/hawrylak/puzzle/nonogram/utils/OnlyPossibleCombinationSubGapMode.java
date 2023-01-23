package org.hawrylak.puzzle.nonogram.utils;

import lombok.Builder;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.SubGap;

@Builder
@Getter
public class OnlyPossibleCombinationSubGapMode {

    public final static OnlyPossibleCombinationSubGapMode NO = OnlyPossibleCombinationSubGapMode.builder().enabled(false).build();

    boolean enabled = true;
    SubGap subGap;
}
