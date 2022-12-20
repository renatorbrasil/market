package com.eventsourcing.market.domain.exception;

public class EventNotSupportedException extends RuntimeException {
    public EventNotSupportedException() {
        super("Event is not supported.");
    }
}
