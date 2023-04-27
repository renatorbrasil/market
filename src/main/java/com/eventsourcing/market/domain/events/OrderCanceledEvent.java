package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;

import java.util.UUID;

@DocumentType("Order.Canceled")
public class OrderCanceledEvent extends DomainEvent {

    public OrderCanceledEvent(UUID aggregateId) {
        super(aggregateId);
    }

}
