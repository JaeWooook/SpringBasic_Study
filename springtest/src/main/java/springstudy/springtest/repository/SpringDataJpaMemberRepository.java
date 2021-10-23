package springstudy.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.springtest.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //interface는 다중 상속이 된다.


    @Override
    Optional<Member> findByName(String name);//이렇게만 해주면 구현이 끝이다.?
}
