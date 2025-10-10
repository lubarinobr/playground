package br.com.playground.order_management.adapters.jpa.mapper;

import br.com.playground.order_management.adapters.jpa.entity.OrderEntity;
import br.com.playground.order_management.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderJpaMapper {

    Order toModel(OrderEntity orderEntity);

    OrderEntity toEntity(Order order);
}
