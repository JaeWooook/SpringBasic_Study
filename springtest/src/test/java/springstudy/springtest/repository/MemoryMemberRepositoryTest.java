package springstudy.springtest.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import springstudy.springtest.domain.Member;

import java.util.List;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //데이터를 청소해주는 코드
    @AfterEach//어떤 메서드가 끝나고 동작하는 콜백 메서드이다.
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();//optional에서 반환타입을 꺼낼때는 get을 사용한다.
        //제대로된 확인
        Assertions.assertThat(member).isEqualTo(result);//static import로 하면 assertThat만으로도 할 수 있다.
        //assertThat이라는 문법이다.
        //That에서 가져올 값과 isEqualTo에 확인할 대상의 값을 넣는다.

        //출력되는 것은 없지만, 다르다면 빨간불이 뜬다.
        //단순한 확인
        System.out.println("result = "+(result==member));
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);//member1을 저장

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);//member2을 저장

        Member result = repository.findByName("spring1").get();//get을 써주기 때문에 optional로 감싸지않는다.
        //get이 알아서 꺼내주기 때문이다.

        Assertions.assertThat(result).isEqualTo(member1);//spring1을 찾는 것을 확인한다.
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();//array의 형태는 list로 받아준다.

        Assertions.assertThat(result.size()).isEqualTo(2);//size는 2개가 되도록 한다 멤버가 2명이기 때문이다.
    }
}
