package com.eventsourcing.market.domain.snapshot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOrderSnapshot {
    private ProductSnapshot product;
    private Long amount;
}
