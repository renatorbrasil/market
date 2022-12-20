package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository extends AggregateRepository<User> {

    @Override
    public User getInstance(UUID userId) {
        return new User(userId);
    }
}
