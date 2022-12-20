package com.eventsourcing.market.domain.model;

import com.eventsourcing.market.domain.model.snapshots.ProductOrderSnapshot;

import java.util.UUID;

public class ProductOrder {
    private Product product;
    private Long amount;

    public ProductOrder(Product product, Long amount) {
        this.product = product;
        this.amount = amount;
    }

    public UUID getProductId() {
        return product.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ProductOrder) &&
                getProductId().equals(((ProductOrder) obj).getProductId());
    }

    public ProductOrderSnapshot getSnapshot() {
        return new ProductOrderSnapshot(product.getSnapshot(), amount);
    }
}
