package com.eventsourcing.market.infrastructure.exception;

import java.util.UUID;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(UUID aggregateId) {
        super(String.format(
                "Concurrency error when persisting new events of aggregate %s",
                aggregateId));
    }
}
