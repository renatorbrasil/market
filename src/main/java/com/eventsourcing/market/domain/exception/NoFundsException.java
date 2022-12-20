package com.eventsourcing.market.domain.exception;

public class NoFundsException extends RuntimeException {
    public NoFundsException() {
        super("Account does not have sufficient funds.");
    }
}
