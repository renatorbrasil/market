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
    private final boolean inStock;
    private final Money price;

    public ProductSnapshot(
            UUID aggregateId,
            String name,
            String description,
            boolean inStock,
            Money price,
            Integer eventNumber
    ) {
        super(aggregateId, eventNumber);
        this.name = name;
        this.description = description;
        this.inStock = inStock;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductSnapshot{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", onStock=" + inStock +
                ", price=" + price +
                '}';
    }
}
