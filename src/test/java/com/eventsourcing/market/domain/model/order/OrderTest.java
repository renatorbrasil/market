package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.Address;
import com.eventsourcing.market.domain.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void itShouldCreateOrderSuccessfully() {
        // Given
        var product1 = new Product("Chair", "For sitting on", new Money(10.0));
        var product2 = new Product("Table", "For sitting on", new Money(100.0));
        var products = List.of(product1, product2);

        var user = new User(new Address("Random Street, London"));

        // When
        var newOrder = new Order(products, user);

        // Then
        assertThat(newOrder.getId()).isNotNull();
    }

    @Test
    void itShouldThrowWhenProductNotAvailable() {
        // Given
        var product1 = new Product("Chair", "For sitting on", new Money(10.0));
        product1.setOutOfStock();

        var product2 = new Product("Table", "For sitting on", new Money(100.0));
        var products = List.of(product1, product2);
        var user = new User(new Address("Random Street, London"));

        // When
        // Then
        assertThatThrownBy(() -> new Order(products, user))
                .isInstanceOf(ProductIsNotAvailableException.class);
    }

    @Test
    void itShouldCalculateOrderPriceCorrectly() {
        // Given
        var product1 = new Product("Chair", "For sitting on", new Money(10.0));
        var product2 = new Product("Table", "For sitting on", new Money(100.0));
        var products = List.of(product1, product2, product2);
        var user = new User(new Address("Random Street, London"));

        // When
        var newOrder = new Order(products, user);

        // Then
        assertThat(newOrder.getTotalPrice()).isEqualTo(new Money(210.0));
    }
}