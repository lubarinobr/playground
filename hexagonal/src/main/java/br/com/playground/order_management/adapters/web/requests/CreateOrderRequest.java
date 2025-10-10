package br.com.playground.order_management.adapters.web.requests;

import java.math.BigDecimal;

public record CreateOrderRequest(BigDecimal total) {
}
