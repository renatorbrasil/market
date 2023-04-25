package com.eventsourcing.market.domain.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOrderSnapshot {
    private ProductSnapshot product;
    private Long amount;
}
