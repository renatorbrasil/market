package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.exception.AggregateNotFoundException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.snapshot.Snapshot;
import com.eventsourcing.market.infrastructure.evenstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public abstract class AggregateRepository<T extends EventSourcedAggregate> {

    @Autowired
    EventStore eventStore;

    public void save(T aggregate) {
        eventStore.storeEventList(aggregate.getChanges(), aggregate);
        aggregate.flushChanges();
    }

    public T findById(UUID aggregateId) {

        var latestSnapshotOptional = eventStore.findLatestSnapshotByAggregateId(aggregateId);

        T aggregate;
        List<DomainEvent> events;
        if (latestSnapshotOptional.isPresent()) {
            var latestSnapshot = latestSnapshotOptional.get();
            events = eventStore.findByAggregateIdFromVersion(aggregateId, latestSnapshot.getEventNumber());
            aggregate = getInstanceFromSnapshot(latestSnapshot);
        } else {
            events = eventStore.findByAggregateIdFromVersion(aggregateId, 0);
            if (events.isEmpty()) throw new AggregateNotFoundException(aggregateId);
            aggregate = getInstance(aggregateId);
        }

        aggregate.apply(events);
        return aggregate;
    }

    public void saveSnapshot(Snapshot snapshot) {
        eventStore.storeSnapshot(snapshot);
    }

    protected abstract T getInstance(UUID aggregateId);

    protected abstract T getInstanceFromSnapshot(Snapshot snapshot);

}
