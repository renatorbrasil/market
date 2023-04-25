package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.snapshot.ProductSnapshot;
import com.eventsourcing.market.domain.snapshot.UserSnapshot;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@DocumentType("Order.Created")
public class OrderCreatedEvent extends DomainEvent {

    public OrderCreatedEvent(UUID aggregateId, Collection<ProductSnapshot> products, UserSnapshot user) {
        super(aggregateId);
        this.user = user;
        this.products = products;
    }

    @Getter
    private final UserSnapshot user;

    @Getter
    private final Collection<ProductSnapshot> products;

}
