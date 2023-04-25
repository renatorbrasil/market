package com.eventsourcing.market.domain.model.product;

import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.Snapshot;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductSnapshot extends Snapshot {
    private String name;
    private String description;
    private boolean onStock;
    private Money price;

    public ProductSnapshot(
            UUID aggregateId,
            String name,
            String description,
            boolean onStock,
            Money price,
            Integer eventNumber
    ) {
        super(aggregateId, eventNumber);
        this.name = name;
        this.description = description;
        this.onStock = onStock;
        this.price = price;
    }
}
