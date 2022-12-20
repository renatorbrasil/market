package com.eventsourcing.market.infrastructure.exception;

import java.util.UUID;

public class InvalidRegisterException extends RuntimeException {
    public InvalidRegisterException() {
        super("Register is in an invalid state.");
    }
}
