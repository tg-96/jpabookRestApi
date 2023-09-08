package jpashop.jpabook.api;

import jpashop.jpabook.domain.Address;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderItem;
import jpashop.jpabook.domain.OrderStatus;
import jpashop.jpabook.repository.OrderRepository;
import jpashop.jpabook.repository.order.query.OrderQueryRepository;
import jpashop.jpabook.service.query.OrderDto;
import jpashop.jpabook.service.query.OrderQueryService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    private final OrderQueryService orderQueryService;
    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3() {
        return orderQueryService.orderV3();
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(@RequestParam(value = "offset",defaultValue = "0")int offset,
                                       @RequestParam(value = "limit",defaultValue = "100")int limit) {
        List<Order> orders = orderRepository.findAllWithMeberDelivery(offset,limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

//    @Data
//    static class OrderDto {
//        private Long orderId;
//        private String name;
//        private LocalDateTime orderDate;
//        private OrderStatus orderStatus;
//        private Address address;
//        private List<OrderItemDto> orderItems;
//
//        public OrderDto(Order order) {
//            orderId = order.getId();
//            name = order.getMember().getName();
//            orderDate = order.getOrderDate();
//            orderStatus = order.getStatus();
//            address = order.getDelivery().getAddress();
//            orderItems = order.getOrderItems()
//                    .stream()
//                    .map(orderItem -> new OrderItemDto(orderItem))
//                    .collect(Collectors.toList());
//        }
//    }
//
//    @Getter
//    static class OrderItemDto {
//        private String itemName;
//        private int orderPrice;
//        private int count;
//
//        public OrderItemDto(OrderItem orderItem) {
//            itemName = orderItem.getItem().getName();
//            orderPrice = orderItem.getOrderPrice();
//            count = orderItem.getCount();
//        }
//    }

}
