package com.eventsourcing.market.infrastructure.evenstore;

import com.eventsourcing.market.domain.base.DomainEvent;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomMongoRepository extends MongoRepository<DomainEvent, UUID> {

    List<DomainEvent> findByAggregateIdOrderByAggregateVersion(UUID aggregateId);

    @Aggregation(pipeline = {
            "{ '$match': {'aggregateId': ?0} }",
            "{ '$sort': {'aggregateVersion': -1} }",
            "{ '$limit': 1}"
    })
    Optional<DomainEvent> findLatestByAggregateId(UUID aggregateId);
}
