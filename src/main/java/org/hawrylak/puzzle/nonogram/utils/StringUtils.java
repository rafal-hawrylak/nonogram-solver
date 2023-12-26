package org.hawrylak.puzzle.nonogram.utils;

public class StringUtils {

    public static String pad(String toPad, char fill, int width) {
        return new String(new char[width - toPad.length()]).replace('\0', fill) + toPad;
    }

}
