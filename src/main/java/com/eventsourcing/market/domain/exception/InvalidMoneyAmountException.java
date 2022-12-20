package com.eventsourcing.market.domain.exception;

public class InvalidMoneyAmountException extends RuntimeException {
    public InvalidMoneyAmountException() {
        super("Money may not have this value.");
    }
}
