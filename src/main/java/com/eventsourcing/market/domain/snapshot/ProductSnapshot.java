package com.eventsourcing.market.domain.snapshot;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.Money;
import lombok.Getter;

import java.util.UUID;

@Getter
@DocumentType("Snapshot.Product")
public class ProductSnapshot extends Snapshot {
    private final String name;
    private final String description;
    private final boolean onStock;
    private final Money price;

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

    @Override
    public String toString() {
        return "ProductSnapshot{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", onStock=" + onStock +
                ", price=" + price +
                '}';
    }
}
