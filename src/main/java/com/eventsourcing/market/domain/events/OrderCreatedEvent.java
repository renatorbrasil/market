package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.order.ProductOrder;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.snapshots.UserSnapshot;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@DocumentType("Order.Created")
public class OrderCreatedEvent extends DomainEvent {

    public OrderCreatedEvent(UUID aggregateId, Set<ProductOrder> productsWithAmount, UserSnapshot user) {
        super(aggregateId);
        this.user = user;
        this.productsWithAmount = productsWithAmount;
    }

    @Getter
    private final UserSnapshot user;

    @Getter
    private final Set<ProductOrder> productsWithAmount;

}
