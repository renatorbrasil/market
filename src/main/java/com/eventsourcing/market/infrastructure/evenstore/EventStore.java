package com.eventsourcing.market.infrastructure.evenstore;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.infrastructure.exception.ConcurrencyException;
import com.eventsourcing.market.infrastructure.exception.InvalidRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Lazy
public class EventStore {

    @Autowired
    CustomMongoRepository mongoRepository;

    public void storeEventList(List<DomainEvent> events, EventSourcedAggregate aggregate) {
        var latestEvent = mongoRepository.findLatestByAggregateId(aggregate.getId());

        if (events.isEmpty()) return;

        if (events.stream()
                .anyMatch(event -> event.getId() == null)) {
            throw new InvalidRegisterException();
        }

        var earliestUncommittedEvent = events.stream()
                .mapToInt(DomainEvent::getEventNumber)
                .min()
                .getAsInt();

        if (latestEvent.isPresent() &&
                latestEvent.get().getEventNumber() >= earliestUncommittedEvent) {
            throw new ConcurrencyException(aggregate.getId());
        }
        mongoRepository.saveAll(events);
    }

    public List<DomainEvent> findByAggregateId(UUID aggregateId) {
        return mongoRepository.findByAggregateIdOrderByEventNumber(aggregateId);
    }

}
