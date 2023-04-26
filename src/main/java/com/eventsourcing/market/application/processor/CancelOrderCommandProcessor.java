package com.eventsourcing.market.application.processor;

import com.eventsourcing.market.application.command.CancelOrderCommand;
import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.domain.exception.DomainException;
import com.eventsourcing.market.domain.model.order.Order;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.repository.ProductRepository;
import com.eventsourcing.market.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelOrderCommandProcessor {

    final OrderRepository orderRepository;

    public void processCommand(CancelOrderCommand command) {
        try {

            var order = orderRepository.findById(command.getOrderId());

            order.cancel();

            orderRepository.save(order);

        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }

    }
}
