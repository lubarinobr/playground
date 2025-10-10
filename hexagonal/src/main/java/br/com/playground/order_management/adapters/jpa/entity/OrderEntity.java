package br.com.playground.order_management.adapters.jpa.entity;

import br.com.playground.order_management.adapters.jpa.converter.OrderNumberConverter;
import br.com.playground.order_management.domain.valueobject.OrderId;
import br.com.playground.order_management.domain.valueobject.OrderNumber;
import br.com.playground.order_management.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity
public class OrderEntity {

    @EmbeddedId
    private OrderId id;
    @Convert(converter = OrderNumberConverter.class)
    private OrderNumber number;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onPersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
