package jpashop.jpabook.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderStatus;
import jpashop.jpabook.domain.QMember;
import jpashop.jpabook.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static jpashop.jpabook.domain.QMember.member;
import static jpashop.jpabook.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;
    //save
    public void save(Order order) {
        em.persist(order);
    }

    //findOne
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

    //findAll
    public List<Order> findAllByString(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                        " where o.status = :status " +
                        "and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Order> findAllWithMeberDelivery() {
        return em.createQuery("select o from Order o" +
                " join fetch o.member m" +
                " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return query
                .select(order)
                .from(order)
                .join(order.member,member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();

    }

    private static BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName)){
            return null;
        }
        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus statusCond){
        if(statusCond == null){
            return null;
        }
        return order.status.eq(statusCond);
    }

    public List<Order> findAllWithMeberDelivery(int offset, int limit) {
        return em.createQuery("select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
                ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }
}
