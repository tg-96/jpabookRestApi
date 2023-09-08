package jpashop.jpabook.service.query;

import jpashop.jpabook.api.OrderApiController;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderQueryService {
    private final OrderRepository orderRepository;
    public List<OrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return result;
    }
}
