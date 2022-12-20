package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.events.OrderCreatedEvent;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.snapshots.ProductOrderSnapshot;
import com.eventsourcing.market.domain.snapshots.UserSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Order extends EventSourcedAggregate {

    private Set<ProductOrder> products;
    private UUID userId;
    private OrderStatus status;

    public Order(Set<ProductOrder> productsWithAmount, UserSnapshot user) {
        super();
        causes(new OrderCreatedEvent(getId(), productsWithAmount, user));
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

    public Money getTotalPrice() {
        return products.stream()
                .map(ProductOrder::getTotalPrice)
                .reduce(new Money(), Money::add);
    }

    private void when(OrderCreatedEvent event) {
        this.status = OrderStatus.PLACED;
        this.userId = event.getUser().getId();
        this.products = event.getProductsWithAmount();
    }


}
