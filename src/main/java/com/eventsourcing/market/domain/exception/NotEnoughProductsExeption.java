package com.eventsourcing.market.domain.exception;

public class NotEnoughProductsExeption extends DomainException {
    public NotEnoughProductsExeption(String productName) {
        super(String.format("Not enough products of name %s available", productName));
    }
}
