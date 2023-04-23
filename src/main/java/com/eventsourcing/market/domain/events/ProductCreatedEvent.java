package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@DocumentType("Product.Created")
public class ProductCreatedEvent extends DomainEvent {

    public ProductCreatedEvent(UUID aggregateId, String name, String description, Money price) {
        super(aggregateId);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final Money price;
}
