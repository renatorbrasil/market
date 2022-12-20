package com.eventsourcing.market.domain.model.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.model.Account;
import com.eventsourcing.market.domain.model.Address;
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
    private Address address;

    @Getter
    private Account account;

}
