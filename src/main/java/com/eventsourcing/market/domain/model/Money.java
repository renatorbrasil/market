package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.exception.InvalidMoneyAmountException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    @Getter
    private BigDecimal value;

    public Money(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyAmountException();
        }
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money(Double value) {
        this(new BigDecimal(value));
    }

    public Money() {
        this(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Money) {
            Money money = (Money) obj;
            return value.equals(money.getValue());
        }
        return false;
    }

    public Money multiplyBy(BigDecimal multiplier) {
        return new Money(value.multiply(multiplier));
    }

    public Money multiplyBy(Double multiplier) {
        return multiplyBy(new BigDecimal(multiplier));
    }

    public BigDecimal divideBy(BigDecimal divisor) {
        return value.divide(divisor, 5, RoundingMode.HALF_EVEN);
    }

    public BigDecimal divideBy(Double divisor) {
        return divideBy(new BigDecimal(divisor));
    }

    public Money add(Money money) {
        return new Money(value.add(money.getValue()));
    }

    public Money subtract(Money money) {
        return new Money(value.subtract(money.getValue()));
    }
}
