package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.events.OrderCanceledEvent;
import com.eventsourcing.market.domain.events.OrderCreatedEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.exception.OrderMayNoLongerBeCanceledException;
import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.snapshot.OrderSnapshot;
import com.eventsourcing.market.domain.snapshot.ProductSnapshot;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Order extends EventSourcedAggregate {

    private Collection<ProductSnapshot> products;
    private UUID userId;
    private OrderStatus status;

    public Order(List<Product> products, User user) {
        super();

        var productSnapshots = products.stream()
                .map(Product::getSnapshot).toList();
        var userSnapshot = user.getSnapshot();

        causes(new OrderCreatedEvent(getId(), productSnapshots, userSnapshot));
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
            if (!product.isInStock()) {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", version=" + version +
                ", products=" + products +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
