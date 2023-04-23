package com.eventsourcing.market.domain.model.product;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.events.ProductCreatedEvent;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.snapshots.ProductSnapshot;

import java.util.UUID;

public class Product extends EventSourcedAggregate {

    private String name;
    private String description;
    private Money price;
    private boolean onStock;

    public Product(String name, String description, Money price) {
        super();
        causes(new ProductCreatedEvent(getId(), name, description, price));
    }

    public Product(UUID id) {
        super(id);
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof ProductCreatedEvent) {
            when((ProductCreatedEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    public boolean isOnStock() {
        return onStock;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(Money price) {
        this.price = price;
    }

    private void when(ProductCreatedEvent event) {
        this.description = event.getDescription();
        this.name = event.getName();
        this.price = event.getPrice();
        this.onStock = true;
    }

    public ProductSnapshot getSnapshot() {
        return new ProductSnapshot(id, name, description, onStock, price);
    }
}
