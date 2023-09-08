package jpashop.jpabook.domain.item;

import jpashop.jpabook.controller.BookForm;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item {
    private String author;
    private String isbn;

    private Book(String name, int price, int stockQuantity, String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
        this.setName(name);
        this.setStockQuantity(stockQuantity);
        this.setPrice(price);
    }

    public Book() {

    }

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book(name, price, stockQuantity, author, isbn);
        return book;
    }

    public void updateBook(BookForm form) {
        this.setPrice(form.getPrice());
        this.setName(form.getName());
        this.setStockQuantity(form.getStockQuantity());
        this.isbn = form.getIsbn();
        this.author = form.getAuthor();
    }
}
