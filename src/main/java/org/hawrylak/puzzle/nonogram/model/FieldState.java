package org.hawrylak.puzzle.nonogram.model;

import lombok.ToString;

@ToString(includeFieldNames = false)
public enum FieldState {
    UNKNOWN,
    EMPTY,
    FULL,
    OUTSIDE
}
