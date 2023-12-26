package org.hawrylak.puzzle.nonogram.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Solution {

    private boolean solved;
    private Puzzle puzzle;
}
