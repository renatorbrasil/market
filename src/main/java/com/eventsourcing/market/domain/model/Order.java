package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.base.EventSourcedAggregate;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.model.events.OrderCreatedEvent;
import com.eventsourcing.market.domain.model.snapshots.ProductOrderSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Order extends EventSourcedAggregate {

    private Map<UUID, ProductOrderSnapshot> products = new HashMap<>();
    private UUID userId;
    private OrderStatus status;

    public Order(Map<Product, Long> productsOrderedWithAmount, User user) {
        super();
        causes(new OrderCreatedEvent(getId(), productsOrderedWithAmount, user));
    }

    public Order(UUID id) {
        super(id);
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof OrderCreatedEvent) {
            when((OrderCreatedEvent) change);
        } else {
            throw new EventNotSupportedException();
        }

    }

    private void when(OrderCreatedEvent event) {
        this.status = OrderStatus.PLACED;
        this.userId = event.getUser().getId();
    }


}
