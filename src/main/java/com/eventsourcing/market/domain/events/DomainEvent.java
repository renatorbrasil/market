package com.eventsourcing.market.domain.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent {

    @Getter
    protected UUID id;

    @Getter
    protected UUID aggregateId;

    @Getter @Setter
    protected Integer eventNumber;

    @Getter
    protected LocalDateTime timestamp;

    public DomainEvent(UUID aggregateId) {
        this.id = UUID.randomUUID();
        this.aggregateId = aggregateId;
        timestamp = LocalDateTime.now();
    }

}
