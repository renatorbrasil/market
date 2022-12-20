package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@DocumentType("Product.Created")
public class ProductCreatedEvent extends DomainEvent {

    public ProductCreatedEvent(UUID aggregateId, String name, Long availableAmount, Money price) {
        super(aggregateId);
        this.name = name;
        this.availableAmount = availableAmount;
        this.price = price;
    }

    @Getter
    private final String name;

    @Getter
    private final Long availableAmount;

    @Getter
    private final Money price;
}
