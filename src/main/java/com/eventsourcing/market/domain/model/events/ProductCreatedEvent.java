package com.eventsourcing.market.domain.model.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.base.DomainEvent;
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
    private String name;

    @Getter
    private Long availableAmount;

    @Getter
    private Money price;
}
