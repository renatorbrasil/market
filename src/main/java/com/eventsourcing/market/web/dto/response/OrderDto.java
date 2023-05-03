package com.eventsourcing.market.web.dto.response;

import com.eventsourcing.market.domain.model.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Collection<ProductDto> products;
    private UUID userId;
    private OrderStatus status;
}
