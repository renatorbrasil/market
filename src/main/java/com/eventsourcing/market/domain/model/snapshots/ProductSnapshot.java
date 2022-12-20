package com.eventsourcing.market.domain.model.snapshots;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductSnapshot {
    private UUID id;
    private String name;
}
