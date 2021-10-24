package springstudy.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springstudy.springtest.domain.Member;
import springstudy.springtest.repository.MemberRepository;
import springstudy.springtest.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    //test에서 같은 인스턴스 리포지토리 사용하기위함
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }//멤버서비스의 리포지토리를 외부에서 넣어주게 바꿔준다?

    //회원가입
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 X

            validateDuplicateMember(member);//중복회원 검증

            memberRepository.save(member);
            return member.getId();//가입하면 id를반환해준다.
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())//optinal로 반환됨
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });//findByName자체가 optional이라서 옵셔널로 감싸지않고, 바로 꺼내준다.
    }

    //전체회원 조회기능
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        } finally {
            long finsih = System.currentTimeMillis();
            long timeMs = finsih-start;
            System.out.println("findMembers = "+timeMs+"ms");
        }
//        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
