package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.model.Snapshot;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.snapshot.UserSnapshot;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository extends AggregateRepository<User> {

    @Override
    public User getInstance(UUID userId) {
        return new User(userId);
    }

    @Override
    public User getInstanceFromSnapshot(Snapshot snapshot) {
        return new User((UserSnapshot) snapshot);
    }
}
