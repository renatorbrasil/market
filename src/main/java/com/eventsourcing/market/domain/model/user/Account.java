package com.eventsourcing.market.domain.model.user;

import com.eventsourcing.market.domain.exception.InvalidMoneyAmountException;
import com.eventsourcing.market.domain.exception.NoFundsException;
import com.eventsourcing.market.domain.model.Money;

import java.math.BigDecimal;

public class Account {

    private Money funds;

    public Account(Money funds) {
        this.funds = funds;
    }

    public Account() {
        this.funds = new Money(BigDecimal.ZERO);
    }

    public Account debit(Money amount) {
        try {
            return new Account(funds.subtract(amount));
        } catch (InvalidMoneyAmountException e) {
            throw new NoFundsException();
        }
    }

    public Account credit(Money amount) {
        return new Account(funds.add(amount));
    }

    @Override
    public String toString() {
        return "Account: " + funds.getValue();
    }
}
