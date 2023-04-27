package com.eventsourcing.market.application.processor;

import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.domain.exception.DomainException;
import com.eventsourcing.market.domain.model.order.Order;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.repository.ProductRepository;
import com.eventsourcing.market.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewOrderCommandProcessor {

    final UserRepository userRepository;
    final ProductRepository productRepository;
    final OrderRepository orderRepository;
    
    public UUID processCommand(NewOrderCommand command) {
        try {
            User user =  userRepository.findById(command.getUserId());

            var products = productRepository.findByIdSet
                    (command.getProductIds());

            Order order = new Order(products, user);

            orderRepository.save(order);

            return order.getId();

        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }

    }
}
