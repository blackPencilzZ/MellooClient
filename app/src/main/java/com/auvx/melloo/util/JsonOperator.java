package com.auvx.melloo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonOperator {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
