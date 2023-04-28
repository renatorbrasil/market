package com.eventsourcing.market.infrastructure.evenstore;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.snapshot.Snapshot;
import com.eventsourcing.market.infrastructure.exception.ConcurrencyException;
import com.eventsourcing.market.infrastructure.exception.InvalidRegisterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Lazy
@RequiredArgsConstructor
public class EventStore {

    @Autowired
    final DomainEventMongoRepository domainEventRepository;

    @Autowired
    final SnapshotMongoRepository snapshotRepository;

    public void storeEventList(List<DomainEvent> events, EventSourcedAggregate aggregate) {

        if (events.isEmpty()) return;

        if (events.stream()
                .anyMatch(event -> event.getId() == null || event.getEventNumber() == null)) {
            throw new InvalidRegisterException();
        }

        var earliestUncommittedEvent = events.stream()
                .mapToInt(DomainEvent::getEventNumber)
                .min()
                .orElse(0);

        var latestEvent = domainEventRepository.findLatestByAggregateId(aggregate.getId());

        if (latestEvent.isPresent() &&
                latestEvent.get().getEventNumber() >= earliestUncommittedEvent) {
            throw new ConcurrencyException(aggregate.getId());
        }
        domainEventRepository.saveAll(events);
    }

    public List<DomainEvent> findByAggregateIdFromVersion(UUID aggregateId, Integer version) {
        return domainEventRepository.findByAggregateIdFromVersion(aggregateId, version);
    }

    public Optional<Snapshot> findLatestSnapshotByAggregateId(UUID aggregateId) {
        return snapshotRepository.findLatestByAggregateId(aggregateId);
    }

    public void storeSnapshot(Snapshot snapshot) {
        snapshotRepository.save(snapshot);
    }

}
