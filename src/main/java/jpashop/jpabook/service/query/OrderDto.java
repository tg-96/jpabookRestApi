package jpashop.jpabook.service.query;

import jpashop.jpabook.api.OrderApiController;
import jpashop.jpabook.domain.Address;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
