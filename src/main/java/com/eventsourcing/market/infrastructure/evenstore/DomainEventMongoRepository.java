package com.eventsourcing.market.infrastructure.evenstore;

import com.eventsourcing.market.domain.events.DomainEvent;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DomainEventMongoRepository extends MongoRepository<DomainEvent, UUID> {

    @Aggregation(pipeline = {
        "{ '$match': {'aggregateId': ?0, 'eventNumber': { $gt: ?1 } } }",
        "{ '$sort': {'eventNumber': -1} }"
    })
    List<DomainEvent> findByAggregateIdFromVersion(UUID aggregateId, Integer version);

    @Aggregation(pipeline = {
            "{ '$match': {'aggregateId': ?0} }",
            "{ '$sort': {'eventNumber': -1} }",
            "{ '$limit': 1 }"
    })
    Optional<DomainEvent> findLatestByAggregateId(UUID aggregateId);
}
