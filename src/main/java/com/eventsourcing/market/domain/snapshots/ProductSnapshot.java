package com.eventsourcing.market.domain.snapshots;

import com.eventsourcing.market.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductSnapshot {
    private UUID id;
    private String name;
    private String description;
    private boolean onStock;
    private Money price;
}
