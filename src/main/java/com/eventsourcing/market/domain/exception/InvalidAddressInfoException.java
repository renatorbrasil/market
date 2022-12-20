package com.eventsourcing.market.domain.exception;

public class InvalidAddressInfoException extends DomainException {
    public InvalidAddressInfoException() {
        super("Address information is valid.");
    }
}
