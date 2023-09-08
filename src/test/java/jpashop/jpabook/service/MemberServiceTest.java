package jpashop.jpabook.service;

import jpashop.jpabook.domain.Member;
import jpashop.jpabook.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("kim");

        //when
        Long savedId = memberService.join(member1);

        //then
        assertEquals(member1, memberRepository.findById(savedId).get());

    }

    @Test
    public void 중복확인() throws Exception {
//given
        Member member1 = new Member();
        member1.setName("kim1");


        Member member2 = new Member();
        member2.setName("kim1");


        //when
            memberService.join(member1);

            try {
                memberService.join(member2);
            }catch (IllegalStateException e){
                return;
            }
        //then
        Assert.fail("예외가 발생해야 한다.");
    }

}