package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import lombok.Getter;

import java.util.UUID;

@DocumentType("Product.Consumed")
public class ProductConsumedEvent extends DomainEvent {

    public ProductConsumedEvent(UUID aggregateId, Long amount) {
        super(aggregateId);
        this.amount = amount;
    }

    @Getter
    private final Long amount;
}
