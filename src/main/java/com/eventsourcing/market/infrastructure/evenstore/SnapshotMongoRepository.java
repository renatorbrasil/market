package com.eventsourcing.market.infrastructure.evenstore;

import com.eventsourcing.market.domain.model.Snapshot;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface SnapshotMongoRepository extends MongoRepository<Snapshot, UUID> {

    @Aggregation(pipeline = {
            "{ '$match': {'aggregateId': ?0} }",
            "{ '$sort': {'eventNumber': -1} }",
            "{ '$limit': 1 }"
    })
    Optional<Snapshot> findLatestByAggregateId(UUID aggregateId);

}
