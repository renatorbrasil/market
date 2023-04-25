package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.Address;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.model.user.UserSnapshot;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void itShouldCreateOrderSuccessfully() {
        // Given
        var productSs1 = new Product("Chair", "For sitting on", new Money(10.0)).getSnapshot();
        var productSs2 = new Product("Table", "For sitting on", new Money(100.0)).getSnapshot();
        var products = List.of(productSs1, productSs2);

        var userSnapshot = new User(new Address("Random Street, London")).getSnapshot();

        // When
        var newOrder = new Order(products, userSnapshot);

        // Then
        assertThat(newOrder.getId()).isNotNull();
    }

    @Test
    void itShouldThrowWhenProductNotAvailable() {
        // Given
        var product1 = new Product("Chair", "For sitting on", new Money(10.0));
        product1.setOutOfStock();
        var productSs1 = product1.getSnapshot();

        var productSs2 = new Product("Table", "For sitting on", new Money(100.0)).getSnapshot();
        var products = List.of(productSs1, productSs2);
        var userSnapshot = new User(new Address("Random Street, London")).getSnapshot();

        // When
        // Then
        assertThatThrownBy(() -> new Order(products, userSnapshot))
                .isInstanceOf(ProductIsNotAvailableException.class);
    }

    @Test
    void itShouldCalculateOrderPriceCorrectly() {
        // Given
        var productSs1 = new Product("Chair", "For sitting on", new Money(10.0)).getSnapshot();
        var productSs2 = new Product("Table", "For sitting on", new Money(100.0)).getSnapshot();
        var products = List.of(productSs1, productSs2, productSs2);
        var userSnapshot = new User(new Address("Random Street, London")).getSnapshot();

        // When
        var newOrder = new Order(products, userSnapshot);

        // Then
        assertThat(newOrder.getTotalPrice()).isEqualTo(new Money(210.0));
    }
}