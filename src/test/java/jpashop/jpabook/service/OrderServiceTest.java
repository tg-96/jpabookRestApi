package jpashop.jpabook.service;

import jpashop.jpabook.domain.*;
import jpashop.jpabook.domain.exception.NotEnoughStockException;
import jpashop.jpabook.domain.item.Book;
import jpashop.jpabook.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember("joon", new Address("양평", "스트릿", "123"));

        Book book = createBook("도로시", 10000, 10,"정태규","123123");

        //when
        Long orderId = orderService.order(member.getId(),book.getId(),2);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 주문 상태는 ORDERS", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문 상품 종류 수가 정확해야 한다.", 1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 price * 상품 수 이다.",20000,getOrder.getTotalPrice());
        assertEquals("주문하면 상품재고가 줄어야 한다.", 8,book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember("joon", new Address("양평", "스트릿", "123"));

        Book book = createBook("도로시", 10000, 10,"정태규","123123");

        //when
        Long orderId = orderService.order(member.getId(),book.getId(),2);
        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);
        //then
        assertEquals("order status가 CANCEL로 바뀌어야 됨.",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소된 상품은 그만큼 재고가 증가되어야 한다",10,book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과() throws Exception{
        //given
        Member member = createMember("joon", new Address("양평", "스트릿", "123"));

        Book book = createBook("도로시", 10000, 10,"정태규","123123");
        //when
        Long orderId = orderService.order(member.getId(),book.getId(),11);

        //then
        fail("재고수량 예외가 발생해야 한다.");
    }

    private Book createBook(String name, int price, int stockQuantity,String author,String isbn) {
        Book book = Book.createBook(name,price,stockQuantity,author,isbn);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}