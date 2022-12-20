package com.eventsourcing.market.domain.model.snapshots;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOrderSnapshot {
    private ProductSnapshot product;
    private Long amount;
}
