package com.eventsourcing.market.application.processor;

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

@Service
@RequiredArgsConstructor
public class NewOrderCommandProcessor {

    final UserRepository userRepository;
    final ProductRepository productRepository;
    final OrderRepository orderRepository;
    
    public void processCommand(NewOrderCommand command) {
        try {
            User user =  userRepository.findById(command.getUserId());

            var products = productRepository.findByIdSet(
                    command.getProductIds());

            var productSnapshots = products.stream()
                    .map(Product::getSnapshot).toList();

            Order order = new Order(productSnapshots, user.getSnapshot());

            orderRepository.save(order);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }

    }
}
