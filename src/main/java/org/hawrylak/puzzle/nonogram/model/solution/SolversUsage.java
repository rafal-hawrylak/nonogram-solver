package org.hawrylak.puzzle.nonogram.model.solution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class SolversUsage {

    private Map<String, Integer> stats = new HashMap<>();
}
