package springstudy.springtest.repository;

import springstudy.springtest.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    //interface의 구현체이다.

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));//Optional로 감싸주면 null일때 클라이언트에서 어떻게 할 수가 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()//람다식?
                .filter(member -> member.getName().equals(name))//1개라도 찾으면 반환한다.
                .findAny();//끝까지 돌았는데 없으면 그대로 반환한다?
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());//store에 있는 member들이 전부 반환
    }

    public void clearStore() {
        store.clear();
    }
}
