package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.base.DomainEvent;
import com.eventsourcing.market.domain.base.EventSourcedAggregate;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.exception.NotEnoughProductsExeption;
import com.eventsourcing.market.domain.model.events.ProductConsumedEvent;
import com.eventsourcing.market.domain.model.events.ProductCreatedEvent;
import com.eventsourcing.market.domain.model.snapshots.ProductSnapshot;
import lombok.Getter;

import java.util.UUID;

public class Product extends EventSourcedAggregate {

    private String name;
    private Long availableAmount;
    private Money price;

    public Product(String name, Long availableAmount, Money price) {
        super();
        causes(new ProductCreatedEvent(getId(), name, availableAmount, price));
    }

    public Product(UUID id) {
        super(id);
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof ProductCreatedEvent) {
            when((ProductCreatedEvent) change);
        } else if (change instanceof ProductConsumedEvent) {
            when((ProductConsumedEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    public void consume(Long quantity) {
        causes(new ProductConsumedEvent(getId(), quantity));
    }

    public void topUp(Long quantity) {
        availableAmount = availableAmount + quantity;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(Money price) {
        this.price = price;
    }

    private void when(ProductConsumedEvent event) {
        if (availableAmount < event.getAmount()) {
            throw new NotEnoughProductsExeption(this.name);
        }

        availableAmount = availableAmount - event.getAmount();
    }

    private void when(ProductCreatedEvent event) {
        this.availableAmount = event.getAvailableAmount();
        this.name = event.getName();
        this.price = event.getPrice();
    }

    public ProductSnapshot getSnapshot() {
        return new ProductSnapshot(id, name);
    }
}
