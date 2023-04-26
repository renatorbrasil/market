package com.eventsourcing.market.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDto {
    private List<UUID> productIds;
    private UUID userId;
}
