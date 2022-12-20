package com.eventsourcing.market.domain.model.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.base.DomainEvent;
import lombok.Getter;

import java.util.UUID;

@DocumentType("Product.Consumed")
public class ProductConsumedEvent extends DomainEvent {

    public ProductConsumedEvent(UUID aggregateId, Long amount) {
        super(aggregateId);
        this.amount = amount;
    }

    @Getter
    private Long amount;
}
