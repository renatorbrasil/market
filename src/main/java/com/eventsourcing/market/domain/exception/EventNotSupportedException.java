package com.eventsourcing.market.domain.exception;

public class EventNotSupportedException extends DomainException {
    public EventNotSupportedException() {
        super("Event is not supported.");
    }
}
