package org.hawrylak.puzzle.nonogram.model.solution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hawrylak.puzzle.nonogram.model.Puzzle;

@AllArgsConstructor
@Getter
public class Solution {

    private boolean solved;
    private Puzzle puzzle;
    private SolversStatistics solversStatistics;
}
