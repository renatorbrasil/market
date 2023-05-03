package com.eventsourcing.market.application.processor;

import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.application.exception.NotFoundException;
import com.eventsourcing.market.application.query.OrderQuery;
import com.eventsourcing.market.domain.exception.AggregateNotFoundException;
import com.eventsourcing.market.domain.exception.DomainException;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.snapshot.OrderSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryProcessor {

    final OrderRepository orderRepository;
    
    public OrderSnapshot processQuery(OrderQuery query) {
        try {

           return orderRepository.findById(query.getOrderId()).getSnapshot();

        } catch (AggregateNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }

    }
}
