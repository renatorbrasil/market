package com.eventsourcing.market.web.advice;

import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.application.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MarketAPIControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(NotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleGeneric(ApplicationException exception) {
        return ResponseEntity.badRequest().build();
    }
}
