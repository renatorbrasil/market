package com.eventsourcing.market.domain.model.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.model.Product;
import com.eventsourcing.market.domain.model.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@DocumentType("Order.Created")
public class OrderCreatedEvent extends DomainEvent {

    public OrderCreatedEvent(UUID aggregateId, Map<Product, Long> productsWithAmount, User user) {
        super(aggregateId);
        this.user = user;
        this.productsWithAmount = productsWithAmount;
    }

    @Getter
    private User user;

    @Getter
    private Map<Product, Long> productsWithAmount;
}
