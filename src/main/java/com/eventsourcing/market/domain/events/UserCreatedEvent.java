package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.user.Account;
import com.eventsourcing.market.domain.model.user.Address;
import lombok.Getter;

import java.util.UUID;

@DocumentType("User.Created")
public class UserCreatedEvent extends DomainEvent {

    public UserCreatedEvent(UUID aggregateId, Account account, Address address) {
        super(aggregateId);
        this.address = address;
        this.account = account;
    }

    @Getter
    private final Address address;

    @Getter
    private final Account account;

}
