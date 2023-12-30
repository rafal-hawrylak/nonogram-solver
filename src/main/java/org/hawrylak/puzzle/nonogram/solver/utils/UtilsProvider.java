package org.hawrylak.puzzle.nonogram.solver.utils;

public class UtilsProvider {

    private static UtilsProvider instance = new UtilsProvider();

    private GapFinder gapFinder = new GapFinder();

    private UtilsProvider() {
    }

    public static UtilsProvider instance() {
        return instance;
    }

    public GapFinder getGapFinder() {
        return gapFinder;
    }

}
