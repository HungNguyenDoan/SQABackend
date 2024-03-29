package com.sqa.banking.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sqa.banking.payload.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorList = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorList.put(error.getField(), error.getDefaultMessage());
        });
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorList);
    }
}
