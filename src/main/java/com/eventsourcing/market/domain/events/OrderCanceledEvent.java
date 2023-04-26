package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.snapshot.ProductSnapshot;
import com.eventsourcing.market.domain.snapshot.UserSnapshot;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@DocumentType("Order.Canceled")
public class OrderCanceledEvent extends DomainEvent {

    public OrderCanceledEvent(UUID aggregateId) {
        super(aggregateId);
    }

}
