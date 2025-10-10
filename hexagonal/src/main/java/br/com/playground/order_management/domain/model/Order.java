package br.com.playground.order_management.domain.model;

import br.com.playground.order_management.domain.valueobject.OrderId;
import br.com.playground.order_management.domain.valueobject.OrderNumber;
import br.com.playground.order_management.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Order {

    private OrderId orderId;
    private OrderNumber number;
    private OrderStatus status;
    private BigDecimal total;

    public void confirm() {
        if (status.canTransitionTo(OrderStatus.CONFIRMED)) {
            this.status = OrderStatus.CONFIRMED;
        }
    }

    public void cancel() {
        if (status.canTransitionTo(OrderStatus.CANCELLED)) {
            this.status = OrderStatus.CANCELLED;
        }
    }

}
