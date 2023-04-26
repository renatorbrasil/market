package com.eventsourcing.market;

import com.eventsourcing.market.application.command.NewOrderCommand;
import com.eventsourcing.market.application.exception.ApplicationException;
import com.eventsourcing.market.application.processor.NewOrderCommandProcessor;
import com.eventsourcing.market.domain.model.Money;
import com.eventsourcing.market.domain.model.product.Product;
import com.eventsourcing.market.domain.model.user.Address;
import com.eventsourcing.market.domain.model.user.User;
import com.eventsourcing.market.domain.repository.OrderRepository;
import com.eventsourcing.market.domain.repository.ProductRepository;
import com.eventsourcing.market.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class MarketApplication {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	NewOrderCommandProcessor commandProcessor;

	public static void main(String[] args) {
		SpringApplication.run(MarketApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		try {
			User user = new User(new Address("Rua Marquês de Maricá"));
			userRepository.save(user);

			Product product1 = new Product("Cadeira", "Cadeira para sentar", new Money(100.0));
			Product product2 = new Product("Mesa", "Mesa para comer em cima", new Money(200.0));
			productRepository.save(product1);
			productRepository.save(product2);

			System.out.println("Product 1: " + product1.getId());
			System.out.println("Product 2: " + product2.getId());

			System.out.println("User: " + user.getId());

/*			var productList = List.of(product1.getId(), product2.getId());

			var newOrderCommand = new NewOrderCommand(productList, user.getId());
			commandProcessor.processCommand(newOrderCommand);*/

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
