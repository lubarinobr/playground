package br.com.playground.order_management.adapters.jpa.converter;

import br.com.playground.order_management.domain.valueobject.OrderNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderNumberConverter implements AttributeConverter<OrderNumber, String> {
    @Override
    public String convertToDatabaseColumn(OrderNumber attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public OrderNumber convertToEntityAttribute(String orderNumber) {
        return orderNumber == null ? null : new OrderNumber(orderNumber);
    }
}
