package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@DocumentType("User.AccountDebit")
public class UserAccountDebitEvent extends DomainEvent {

    public UserAccountDebitEvent(UUID aggregateId, Money amount) {
        super(aggregateId);
        this.amount = amount;
    }

    @Getter
    private final Money amount;
}