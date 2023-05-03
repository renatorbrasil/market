package com.eventsourcing.market.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderQuery {
    private UUID orderId;
}
