package org.hawrylak.puzzle.nonogram.guess;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Guesses {

    private Optional<Checkpoint> checkpoint = Optional.empty();

}
