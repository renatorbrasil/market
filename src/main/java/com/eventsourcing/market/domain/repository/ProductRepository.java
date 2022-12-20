package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProductRepository extends AggregateRepository<Product> {

    @Override
    public Product getInstance(UUID productId) {
        return new Product(productId);
    }
}
