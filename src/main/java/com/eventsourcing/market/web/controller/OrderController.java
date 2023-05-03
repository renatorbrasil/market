package com.eventsourcing.market.web.controller;

import com.eventsourcing.market.application.command.CancelOrderCommand;
import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.application.processor.CancelOrderCommandProcessor;
import com.eventsourcing.market.application.processor.NewOrderCommandProcessor;
import com.eventsourcing.market.application.processor.OrderQueryProcessor;
import com.eventsourcing.market.application.query.OrderQuery;
import com.eventsourcing.market.web.dto.request.CancelOrderDto;
import com.eventsourcing.market.web.dto.request.NewOrderDto;
import com.eventsourcing.market.web.dto.response.OrderCreatedDto;
import com.eventsourcing.market.web.dto.response.OrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    NewOrderCommandProcessor newOrderCommandProcessor;

    @Autowired
    CancelOrderCommandProcessor cancelOrderCommandProcessor;

    @Autowired
    OrderQueryProcessor orderQueryProcessor;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public OrderCreatedDto postOrder(@RequestBody NewOrderDto newOrder) {
        var command = new NewOrderCommand(newOrder.getProductIds(), newOrder.getUserId());
        var userId = newOrderCommandProcessor.processCommand(command);
        return new OrderCreatedDto(userId);
    }

    @PutMapping("/cancel")
    public void cancelOrder(@RequestBody CancelOrderDto cancelOrder) {
        var command = new CancelOrderCommand(cancelOrder.getOrderId());
        cancelOrderCommandProcessor.processCommand(command);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable UUID orderId) {
        var query = new OrderQuery(orderId);
        var order = orderQueryProcessor.processQuery(query);

        return modelMapper.map(order, OrderDto.class);
    }
}
