package br.com.playground.order_management.adapters.web;

import br.com.playground.order_management.adapters.web.requests.CreateOrderRequest;
import br.com.playground.order_management.application.ports.in.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<UUID> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.ok(createOrderUseCase.handle(createOrderRequest.total()));
    }

}
