package com.eventsourcing.market.domain.repository;

import com.eventsourcing.market.domain.model.product.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class ProductRepository extends AggregateRepository<Product> {

    @Override
    public Product getInstance(UUID productId) {
        return new Product(productId);
    }

    public List<Product> findByIdSet(Set<UUID> productIds) {
        List<Product> products = new ArrayList<>();
        productIds.forEach(id -> {
            Product product = findById(id);
            products.add(product);
        });
        return products;
    }

    public void saveAll(List<Product> products) {
        products.forEach(this::save);
    }
}
