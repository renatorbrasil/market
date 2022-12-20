package com.eventsourcing.market.domain.exception;

public class InvalidAddressInfoException extends RuntimeException {
    public InvalidAddressInfoException() {
        super("Address information is valid.");
    }
}
