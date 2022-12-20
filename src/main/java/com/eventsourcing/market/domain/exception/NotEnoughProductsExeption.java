package com.eventsourcing.market.domain.exception;

import java.util.UUID;

public class NotEnoughProductsExeption extends DomainException {
    public NotEnoughProductsExeption(String productName) {
        super(String.format("Not enough products of name %s available", productName));
    }
}
