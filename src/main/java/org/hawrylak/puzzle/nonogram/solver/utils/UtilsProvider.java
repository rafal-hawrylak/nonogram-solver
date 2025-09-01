package org.hawrylak.puzzle.nonogram.solver.utils;

import lombok.Getter;

@Getter
public class UtilsProvider {

    private static UtilsProvider instance = new UtilsProvider();

    private GapFinder gapFinder = new GapFinder();
    private FieldFinder fieldFinder = new FieldFinder();
    private RowSelector rowSelector = new RowSelector();
    private NumberSelector numberSelector = new NumberSelector();
    private GapFiller gapFiller = new GapFiller(fieldFinder, numberSelector, gapFinder);
    private GapCloser gapCloser = new GapCloser(fieldFinder, gapFiller, numberSelector);

    private UtilsProvider() {
    }

    public static UtilsProvider instance() {
        return instance;
    }

}
