package org.hawrylak.puzzle.nonogram.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.statistics.SolversStatistics;

@AllArgsConstructor
@Getter
public class Solution {

    private boolean solved;
    private Puzzle puzzle;
    private SolversStatistics solversStatistics;
}
