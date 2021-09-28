package springstudy.springtest.repository;

import springstudy.springtest.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    //Optional은 findById, findByName이 null일 경우를 대비해서 감싸서 반환한다.
    List<Member> findAll();
}
