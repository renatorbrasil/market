package com.eventsourcing.market.domain.events;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@DocumentType("Product.OutOfStock")
public class ProductOutOfStockEvent extends DomainEvent {

    public ProductOutOfStockEvent(UUID aggregateId) {
        super(aggregateId);
    }
}
