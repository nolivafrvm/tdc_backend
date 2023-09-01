package com.tdc.sensorApp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
public class ErrorMessage {
    private String code;
    private List<Map<String, String>> messages;

    public static String formatMessage(BindingResult result) {
        log.info("Checking Error for objects errors");
        List<Map<String,String>> errors = result.getFieldErrors()
                .stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("04")
                .messages(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = "";
        try {
            toJson = objectMapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toJson;
    }
}


