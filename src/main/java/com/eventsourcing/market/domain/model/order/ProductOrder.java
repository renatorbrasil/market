package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.snapshots.ProductSnapshot;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductOrder {
    private final ProductSnapshot product;
    private final Long amount;

    public ProductOrder(ProductSnapshot product, Long amount) {
        this.product = product;
        this.amount = amount;
    }

    public UUID getProductId() {
        return product.getId();
    }

    public Money getTotalPrice() {
        return product.getPrice().multiplyBy(BigDecimal.valueOf(amount));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ProductOrder) &&
                getProductId().equals(((ProductOrder) obj).getProductId());
    }
}
