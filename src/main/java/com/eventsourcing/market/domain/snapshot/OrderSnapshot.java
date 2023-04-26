package com.eventsourcing.market.domain.snapshot;

import com.eventsourcing.market.config.mongo.DocumentType;
import com.eventsourcing.market.domain.model.order.OrderStatus;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
@DocumentType("Snapshot.Order")
public class OrderSnapshot extends Snapshot {

    private final Collection<ProductSnapshot> products;
    private final UUID userId;
    private final OrderStatus status;

    public OrderSnapshot(
        UUID aggregateId,
        Collection<ProductSnapshot> products,
        UUID userId,
        OrderStatus status,
        Integer eventNumber
    ) {
        super(aggregateId, eventNumber);
        this.products = products;
        this.userId = userId;
        this.status = status;
    }
}
