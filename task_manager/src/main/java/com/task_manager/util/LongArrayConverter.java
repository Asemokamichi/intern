package com.task_manager.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class LongArrayConverter implements AttributeConverter<Long[], String> {
    @Override
    public String convertToDatabaseColumn(Long[] responsibles) {
        if (responsibles == null) {
            return null;
        }
        return Arrays.stream(responsibles)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public Long[] convertToEntityAttribute(String responsiblesString) {
        if (responsiblesString == null || responsiblesString.isEmpty()) {
            return new Long[0];
        }
        return Arrays.stream(responsiblesString.split(","))
                .map(Long::valueOf)
                .toArray(Long[]::new);
    }
}
