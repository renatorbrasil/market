package com.eventsourcing.market.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class NewOrderCommand {
    private Map<UUID, Long> productOrders;
    private UUID userId;
}
