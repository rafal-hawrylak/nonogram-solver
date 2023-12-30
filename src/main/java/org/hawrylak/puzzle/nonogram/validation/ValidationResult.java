package org.hawrylak.puzzle.nonogram.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ValidationResult {

    public static final ValidationResult OK = new ValidationResult(true);
    private final boolean valid;
    private String message;

}
