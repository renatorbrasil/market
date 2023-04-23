package com.eventsourcing.market.domain.model.order;

import com.eventsourcing.market.domain.exception.ProductIsNotAvailableException;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.user.Address;
import com.eventsourcing.market.domain.snapshots.ProductSnapshot;
import com.eventsourcing.market.domain.snapshots.UserSnapshot;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void itShouldCreateOrderSuccessfully() {
        // Given
        var productSs1 = new ProductSnapshot(UUID.randomUUID(), "Chair", "For sitting on", true, new Money(10.0));
        var productSs2 = new ProductSnapshot(UUID.randomUUID(), "Table", "For sitting on", true, new Money(100.0));
        var products = List.of(productSs1, productSs2);
        var user = new UserSnapshot(UUID.randomUUID(), new Address("Random Street, London"));

        // When
        var newOrder = new Order(products, user);

        // Then
        assertThat(newOrder.getId()).isNotNull();
    }

    @Test
    void itShouldThrowWhenProductNotAvailable() {
        // Given
        var productSs1 = new ProductSnapshot(UUID.randomUUID(), "Chair", "For sitting on", true, new Money(10.0));
        var productSs2 = new ProductSnapshot(UUID.randomUUID(), "Table", "For sitting on", false, new Money(100.0));
        var products = List.of(productSs1, productSs2);
        var user = new UserSnapshot(UUID.randomUUID(), new Address("Random Street, London"));

        // When
        // Then
        assertThatThrownBy(() -> new Order(products, user))
                .isInstanceOf(ProductIsNotAvailableException.class);
    }

    @Test
    void itShouldCalculateOrderPriceCorrectly() {
        // Given
        var productSs1 = new ProductSnapshot(UUID.randomUUID(), "Chair", "For sitting on", true, new Money(10.0));
        var productSs2 = new ProductSnapshot(UUID.randomUUID(), "Table", "For sitting on", true, new Money(100.0));
        var products = List.of(productSs1, productSs2, productSs2);
        var user = new UserSnapshot(UUID.randomUUID(), new Address("Random Street, London"));

        // When
        var newOrder = new Order(products, user);

        // Then
        assertThat(newOrder.getTotalPrice()).isEqualTo(new Money(210.0));
    }
}