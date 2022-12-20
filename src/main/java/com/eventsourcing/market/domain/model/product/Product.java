package com.eventsourcing.market.domain.model.product;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.exception.NotEnoughProductsExeption;
import com.eventsourcing.market.domain.events.ProductConsumedEvent;
import com.eventsourcing.market.domain.events.ProductCreatedEvent;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.snapshots.ProductSnapshot;

import java.math.BigDecimal;
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

    public Money getPriceWithAmount(Long amount) {
        return price.multiplyBy(BigDecimal.valueOf(amount));
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
        return new ProductSnapshot(id, name, availableAmount, price);
    }
}
