package springstudy.springtest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springstudy.springtest.domain.Member;
import springstudy.springtest.repository.MemoryMemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;

    MemoryMemberRepository memberRepository;
    //객체를 새로 선언해주는것은 다른 객체?를 사용하는 것이다.
    //멤버서비스에 있는 리포지토리랑 서로 다른 객체이다. 서로 다른 인스턴스다.
    //하지만 현재 store가 static으로 되어있기 때문에 문제는없지만 static이 아니라면 문제가 될 수 있다.

    @BeforeEach
    public void beforeEach() {//test를 실행하기전에 리포지토리를 만들어준다.
        memberRepository = new MemoryMemberRepository();
        memberService  = new MemberService(memberRepository);
    }


    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}