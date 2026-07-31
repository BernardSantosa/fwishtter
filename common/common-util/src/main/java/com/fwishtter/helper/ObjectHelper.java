package com.fwishtter.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectHelper {

    private static final ObjectMapper mapper = createObjectMapper();
    private ObjectHelper() {}

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ObjectMapper objectMapper() {
        return mapper;
    }

    public static ObjectWriter objectWriter() {
        return mapper.writer();
    }
}
