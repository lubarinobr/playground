package br.com.playground.order_management.application.ports.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateOrderUseCase {
    UUID handle(BigDecimal amount);
}
