package com.student.registration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestControllerAdvice
public class StudentControllerAdvice {

    private final ObjectMapper mapper;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) throws JsonProcessingException {
        return new ResponseEntity<>(mapper.writeValueAsString(ex.getMessage()), ex.getStatus());
    }
}
