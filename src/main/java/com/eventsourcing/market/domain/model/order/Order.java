package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.events.OrderCanceledEvent;
import com.eventsourcing.market.domain.events.OrderCreatedEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.exception.OrderMayNoLongerBeCanceledException;
import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.snapshot.OrderSnapshot;
import com.eventsourcing.market.domain.snapshot.ProductSnapshot;
import com.eventsourcing.market.domain.snapshot.UserSnapshot;

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

    public Order(OrderSnapshot snapshot) {
        super(snapshot.getAggregateId(), snapshot.getEventNumber());
        this.products = snapshot.getProducts();
        this.userId = snapshot.getUserId();
        this.status = snapshot.getStatus();
    }

    public Money getTotalPrice() {
        return products.stream()
                .map(ProductSnapshot::getPrice)
                .reduce(new Money(), Money::add);
    }

    public void cancel() {
        causes(new OrderCanceledEvent(getId()));
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof OrderCreatedEvent) {
            when((OrderCreatedEvent) change);
        } else if (change instanceof OrderCanceledEvent) {
            when((OrderCanceledEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    public OrderSnapshot getSnapshot() {
        return new OrderSnapshot(getId(), products, userId, status, version);
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

    private void when(OrderCanceledEvent event) {
        if (!status.equals(OrderStatus.PLACED)) {
            throw new OrderMayNoLongerBeCanceledException(event.getAggregateId());
        }
        this.status = OrderStatus.CANCELED;
    }

}
