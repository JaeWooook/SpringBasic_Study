package springstudy.springtest.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springstudy.springtest.domain.Member;
import springstudy.springtest.repository.MemberRepository;
import springstudy.springtest.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//스프링컨테이너와 테스트를 함께 실행한다. 진짜 스프링에서 테스트를 실행하는 것이다.
@Transactional
//DB를 롤백을 하기위해 Transactional을 적어준다. 서비스에붙었을 때는 롤백하지않고, 테스트 케이스에서만 롤백해준다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //기존 코드들은 생성된 injection이 좋은데 test는 다른곳에서 가져다 쓸게 아니기 때문에 테스트에서는 필드로받기위해
    //Autowired를 사용한다.

    @Test
    void join() {
        //given 무언가가 주어져서
        Member member = new Member();
        member.setName("spring");

        //when 이럴때?
        Long saveId = memberService.join(member);//저장한게 레포지토리인게 맞는지 확인하기위함

        //then 결과가 이것이 나와야한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //Assertions.assertThrows(IllegalStateException.class, ()-> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, ()-> memberService.join(member2));//이게 실행되면 에러가 발생해야한다.
        //message까지 확인하고싶다면 반환이 되기 때문에 아래 처럼 확인하면된다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //IllegalStateException이 발생해야한다.
        //만약에 assertThrows안에 NullException하면 틀렸다고 나온다.


//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//        } catch (IllegalStateException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }
}