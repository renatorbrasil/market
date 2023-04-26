package com.eventsourcing.market.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CancelOrderCommand {
    private UUID orderId;
}
