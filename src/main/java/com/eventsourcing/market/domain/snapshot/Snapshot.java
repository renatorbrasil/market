package com.eventsourcing.market.domain.snapshot;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Snapshot {
    protected UUID id;
    protected UUID aggregateId;
    protected Integer eventNumber;
    protected LocalDateTime timestamp;

    public Snapshot(UUID aggregateId, Integer eventNumber) {
        id = UUID.randomUUID();
        this.aggregateId = aggregateId;
        this.eventNumber = eventNumber;
        timestamp = LocalDateTime.now();
    }
}
