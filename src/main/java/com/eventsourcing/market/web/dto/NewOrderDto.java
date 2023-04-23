package com.eventsourcing.market.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NewOrderDto {
    private List<UUID> productIds;
    private UUID userId;
}
