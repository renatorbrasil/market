package com.eventsourcing.market.domain.exception;

import java.util.UUID;

public class OrderMayNoLongerBeCanceledException extends DomainException {
    public OrderMayNoLongerBeCanceledException(UUID orderId) {
        super(String.format("Order of id %s may no longer be canceled", orderId));
    }
}
