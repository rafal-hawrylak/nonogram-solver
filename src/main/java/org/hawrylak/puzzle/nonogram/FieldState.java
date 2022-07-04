package org.hawrylak.puzzle.nonogram;

import lombok.ToString;

@ToString(includeFieldNames = false)
public enum FieldState {
    UNKNOWN,
    EMPTY,
    FULL,
    OUTSIDE
}
