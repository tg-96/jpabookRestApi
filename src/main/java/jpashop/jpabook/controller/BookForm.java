package jpashop.jpabook.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookForm {
    private Long id;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
