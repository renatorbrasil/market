package com.eventsourcing.market.domain.exception;

import java.util.UUID;

public class AggregateNotFoundException extends DomainException {
    public AggregateNotFoundException(UUID aggregateId) {
        super(String.format("Aggregate of id %s does not exist.", aggregateId));
    }
}
