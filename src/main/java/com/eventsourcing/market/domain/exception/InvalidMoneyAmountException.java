package com.eventsourcing.market.domain.exception;

public class InvalidMoneyAmountException extends DomainException {
    public InvalidMoneyAmountException() {
        super("Money may not have this value.");
    }
}
