package jpashop.jpabook.repository.order.simpleQuery;

import jpashop.jpabook.domain.Address;
import jpashop.jpabook.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQureyDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQureyDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address){
            this.orderId = orderId;
            this.name = name;
            this.orderDate = orderDate;
            this.orderStatus = orderStatus;
            this.address = address;
        }
}
