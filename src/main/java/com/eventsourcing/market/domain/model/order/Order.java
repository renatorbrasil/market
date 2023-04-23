package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.events.OrderCreatedEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.snapshots.ProductSnapshot;
import com.eventsourcing.market.domain.snapshots.UserSnapshot;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Order extends EventSourcedAggregate {

    private Collection<ProductSnapshot> products;
    private UUID userId;
    private OrderStatus status;

    public Order(List<ProductSnapshot> products, UserSnapshot user) {
        super();
        causes(new OrderCreatedEvent(getId(), products, user));
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
                .map(ProductSnapshot::getPrice)
                .reduce(new Money(), Money::add);
    }

    private void when(OrderCreatedEvent event) {
        event.getProducts().forEach(product -> {
            if (!product.isOnStock()) {
                throw new ProductIsNotAvailableException(product.getId());
            }
        });
        this.status = OrderStatus.PLACED;
        this.userId = event.getUser().getId();
        this.products = event.getProducts();
    }


}
