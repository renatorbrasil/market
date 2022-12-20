package com.eventsourcing.market;

import com.eventsourcing.market.domain.model.*;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.repository.ProductRepository;
import com.eventsourcing.market.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
public class MarketApplication {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

		User user = new User(new Address("Rua Marquês de Maricá"), new Account(new Money(50.0)));
		user.makePayment(new Money(10.0));
		user.changeAddress(new Address("Rua Alvarenga Peixoto"));

		userRepository.save(user);

		Product product1 = new Product("Cadeira", 100L, new Money(100.0));
		Product product2 = new Product("Mesa", 50L, new Money(200.0));

		System.out.println(product1.getId());

		productRepository.save(product1);
		productRepository.save(product2);

		var productMap = new HashMap<Product, Long>();
		productMap.put(product1, 20L);
		productMap.put(product2, 2L);

		Order order = new Order(productMap, user);
		orderRepository.save(order);
	}

}
