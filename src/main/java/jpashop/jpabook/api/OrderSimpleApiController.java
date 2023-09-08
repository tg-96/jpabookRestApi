package jpashop.jpabook.api;

import jpashop.jpabook.domain.Address;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderStatus;
import jpashop.jpabook.repository.OrderRepository;
import jpashop.jpabook.repository.OrderSearch;
import jpashop.jpabook.repository.order.simpleQuery.OrderSimpleQueryRepository;
import jpashop.jpabook.repository.order.simpleQuery.OrderSimpleQureyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne에서의 성능 최적화
 * Order
 * Order->Member
 * Order->Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    //무한루프
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orders(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<SimpleOrderDto> orderDto = orders. stream()
                .map(o->new SimpleOrderDto(o))
                .collect(Collectors.toList());
        Result result = new Result(orderDto);
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public Result orderV3(){
        List<Order> orders = orderRepository.findAllWithMeberDelivery();
        List<SimpleOrderDto> orderDto = orders.stream()
                .map(o->new SimpleOrderDto(o))
                .collect(Collectors.toList());
        Result result = new Result(orderDto);
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQureyDto> orderV4(){
        return orderSimpleQueryRepository.findOrderDto();
    }


    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
