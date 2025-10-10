package br.com.playground.order_management.config;

import br.com.playground.order_management.application.ports.in.CreateOrderUseCase;
import br.com.playground.order_management.application.service.CreateOrderService;
import br.com.playground.order_management.domain.ports.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderBeans {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderRepository orderRepository
    ) {
        return new CreateOrderService(orderRepository);
    }
}
