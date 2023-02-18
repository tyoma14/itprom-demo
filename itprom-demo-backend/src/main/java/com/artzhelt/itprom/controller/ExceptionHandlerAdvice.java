package com.artzhelt.itprom.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ProblemDetail handleRemovalException(DataIntegrityViolationException e, HttpMethod httpMethod) {
        if (httpMethod.equals(HttpMethod.DELETE)) {
            String detail = "Удаление невозможно: данный элемент используется другими элементами";
            return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), detail);
        }
        throw e;
    }

}
