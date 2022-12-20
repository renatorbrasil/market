package com.eventsourcing.market.domain.exception;

public class NoFundsException extends DomainException {
    public NoFundsException() {
        super("Account does not have sufficient funds.");
    }
}
