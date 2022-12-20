package com.eventsourcing.market.application.processor;

import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.domain.model.order.Order;
import com.eventsourcing.market.domain.model.order.ProductOrder;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.repository.ProductRepository;
import com.eventsourcing.market.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewOrderCommandProcessor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    OrderRepository orderRepository;
    
    public void processCommand(NewOrderCommand command) {
        User user =  userRepository.findById(command.getUserId());
        
        List<Product> products = productRepository.findByIdSet(
                command.getProductOrders().keySet());

        Set<ProductOrder> productOrders = new HashSet<>();
        for (Product product : products) {
            var amount = command.getProductOrders().get(product.getId());
            product.consume(amount);
            productOrders.add(new ProductOrder(product.getSnapshot(), amount));
        }

        Order order = new Order(productOrders, user.getSnapshot());
        user.makePayment(order.getTotalPrice());

        userRepository.save(user);
        productRepository.saveAll(products);
        orderRepository.save(order);
    }
}
