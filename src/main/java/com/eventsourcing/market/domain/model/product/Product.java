package com.eventsourcing.market.domain.model.product;

import com.eventsourcing.market.domain.events.DomainEvent;
import com.eventsourcing.market.domain.events.ProductCreatedEvent;
import com.eventsourcing.market.domain.events.ProductOutOfStockEvent;
import com.eventsourcing.market.domain.exception.EventNotSupportedException;
import com.eventsourcing.market.domain.model.EventSourcedAggregate;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.snapshot.ProductSnapshot;

import java.util.UUID;

public class Product extends EventSourcedAggregate {

    private String name;
    private String description;
    private Money price;
    private boolean inStock;

    public Product(String name, String description, Money price) {
        super();
        causes(new ProductCreatedEvent(getId(), name, description, price));
    }

    public Product(UUID id) {
        super(id);
    }

    public Product(ProductSnapshot snapshot) {
        super(snapshot.getAggregateId(), snapshot.getEventNumber());
        this.name = snapshot.getName();
        this.description = snapshot.getDescription();
        this.price = snapshot.getPrice();
        this.inStock = snapshot.isInStock();
    }

    @Override
    protected void applyEvent(DomainEvent change) {
        if (change instanceof ProductCreatedEvent) {
            when((ProductCreatedEvent) change);
        } else if (change instanceof ProductOutOfStockEvent) {
            when((ProductOutOfStockEvent) change);
        } else {
            throw new EventNotSupportedException();
        }
    }

    private void when(ProductCreatedEvent event) {
        this.description = event.getDescription();
        this.name = event.getName();
        this.price = event.getPrice();
        this.inStock = true;
    }

    private void when(ProductOutOfStockEvent event) {
        this.inStock = false;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setOutOfStock() {
        causes(new ProductOutOfStockEvent(getId()));
    }

    public ProductSnapshot getSnapshot() {
        return new ProductSnapshot(id, name, description, inStock, price, version);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", onStock=" + inStock +
                '}';
    }
}
