package com.eventsourcing.market.web.controller;

import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.application.processor.NewOrderCommandProcessor;
import com.eventsourcing.market.web.dto.NewOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
public class OrderController {

    @Autowired
    NewOrderCommandProcessor newOrderCommandProcessor;

    @PostMapping
    public void postOrder(NewOrderDto newOrder) {
        var command = new NewOrderCommand(newOrder.getProductIds(), newOrder.getUserId());
        newOrderCommandProcessor.processCommand(command);
    }
}
