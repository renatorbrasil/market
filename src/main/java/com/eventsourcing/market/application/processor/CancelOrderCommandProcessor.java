package com.eventsourcing.market.application.processor;

import com.eventsourcing.market.application.command.CancelOrderCommand;
import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.domain.exception.DomainException;
import com.eventsourcing.market.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
