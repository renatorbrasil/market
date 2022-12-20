package com.eventsourcing.market.domain.base;

import lombok.Getter;
import lombok.Setter;

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

    protected abstract void applyEvent(DomainEvent change);

    public void apply(DomainEvent change) {
        version++;
        change.setAggregateVersion(version);
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
