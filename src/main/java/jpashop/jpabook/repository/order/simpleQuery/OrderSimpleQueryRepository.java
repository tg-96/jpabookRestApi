package jpashop.jpabook.repository.order.simpleQuery;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@Data
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQureyDto> findOrderDto() {
        return em.createQuery("select new jpashop.jpabook.repository.order.simpleQuery" +
                        ".OrderSimpleQureyDto(o.id,m.name,o.orderDate,o.status,d.address) from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQureyDto.class)
                .getResultList();
    }
}
