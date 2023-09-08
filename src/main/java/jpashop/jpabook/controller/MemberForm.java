package jpashop.jpabook.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원이름은 필수 입니다.")
    private String name;

    @Size(min=3, message = "3글자 이상을 작성하세요.")
    private String city;
    private String street;
    private String zipcode;
}
