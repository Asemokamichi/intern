package com.task_manager.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Convert {
    public static <T, R> List<R> toDto(List<T> entities, Function<T, R> converter) {
        return entities.stream()
                .map(converter)
                .collect(Collectors.toList());
    }
}
