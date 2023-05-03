package com.eventsourcing.market.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private boolean inStock;
    private MoneyDto price;
}
