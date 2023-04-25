package com.eventsourcing.market.infrastructure.exception;

public class InvalidRegisterException extends RuntimeException {
    public InvalidRegisterException() {
        super("Register is in an invalid state.");
    }
}
