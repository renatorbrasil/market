package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.model.Snapshot;
import com.eventsourcing.market.domain.model.product.ProductSnapshot;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class OrderSnapshot extends Snapshot {

    private Collection<ProductSnapshot> products;
    private UUID userId;
    private OrderStatus status;

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
