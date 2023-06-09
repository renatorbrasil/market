package com.eventsourcing.market.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class NewOrderCommand {
    private List<UUID> productIds;
    private UUID userId;
}
