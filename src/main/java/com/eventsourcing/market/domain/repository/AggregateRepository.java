package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.base.EventSourcedAggregate;
import com.eventsourcing.market.infrastructure.evenstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class AggregateRepository<T extends EventSourcedAggregate> {

    @Autowired
    EventStore eventStore;

    public void save(T aggregate) {
        eventStore.storeEventList(aggregate.getChanges(), aggregate);
        aggregate.flushChanges();
    }

    public T findById(UUID aggregateId) {
        var events = eventStore.findByAggregateId(aggregateId);
        T aggregate = getInstance(aggregateId);
        aggregate.apply(events);
        return aggregate;
    }

    public abstract T getInstance(UUID aggregateId);

}
