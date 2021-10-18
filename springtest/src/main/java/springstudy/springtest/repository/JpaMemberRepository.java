package springstudy.springtest.repository;

import springstudy.springtest.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);//persist가 영속하다 영구저장한다는 의미이다.
        return member;//이렇게하면 insert 쿼리만들어서 member에 id까지 만들어서 알아서 넣어준다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//조회할 타입이랑, 식별자 id값 넣어주면 select문생성한다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
