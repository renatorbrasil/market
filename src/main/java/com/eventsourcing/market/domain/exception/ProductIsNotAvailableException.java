package com.eventsourcing.market.domain.exception;

import java.util.UUID;

public class ProductIsNotAvailableException extends DomainException {
    public ProductIsNotAvailableException(UUID productId) {
        super(String.format("Product of id %s is not available.", productId));
    }
}
