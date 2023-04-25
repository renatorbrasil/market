package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.model.Snapshot;
import com.eventsourcing.market.domain.model.order.Order;
import com.eventsourcing.market.domain.model.order.OrderSnapshot;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OrderRepository extends AggregateRepository<Order> {

    @Override
    public Order getInstance(UUID orderId) {
        return new Order(orderId);
    }

    @Override
    public Order getInstanceFromSnapshot(Snapshot snapshot) {
        return new Order((OrderSnapshot) snapshot);
    }
}
