package springstudy.springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springstudy.springtest.repository.MemberRepository;
import springstudy.springtest.repository.MemoryMemberRepository;
import springstudy.springtest.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());//리포지토리를넣어줘야해서 에러가발생한다.
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
