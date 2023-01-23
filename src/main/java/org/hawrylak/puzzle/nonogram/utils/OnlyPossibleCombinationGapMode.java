package org.hawrylak.puzzle.nonogram.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OnlyPossibleCombinationGapMode {

    public final static OnlyPossibleCombinationGapMode NO = OnlyPossibleCombinationGapMode.builder().enabled(false).build();

    private boolean enabled = true;

    private boolean startingFrom;
    private boolean endingAt;
}
