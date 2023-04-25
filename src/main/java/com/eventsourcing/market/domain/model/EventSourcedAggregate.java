package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.events.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EventSourcedAggregate extends Entity {

    @Getter
    protected List<DomainEvent> changes = new ArrayList<>();

    @Getter
    protected int version = 0;

    public EventSourcedAggregate() {
        super();
    }

    public EventSourcedAggregate(UUID id) {
        super(id);
    }

    public EventSourcedAggregate(UUID id, Integer version) {
        super(id);
        this.version = version;
    }

    protected abstract void applyEvent(DomainEvent change);

    public void apply(DomainEvent change) {
        version++;
        change.setEventNumber(version);
        applyEvent(change);
    }

    public void apply(List<DomainEvent> changes) {
        changes.forEach(this::apply);
    }

    protected void causes(DomainEvent event) {
        apply(event);
        this.changes.add(event);
    }

    public void flushChanges() {
        this.changes.clear();
    }
}
