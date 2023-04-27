package com.eventsourcing.market.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderCreatedDto {
    UUID orderId;
}
