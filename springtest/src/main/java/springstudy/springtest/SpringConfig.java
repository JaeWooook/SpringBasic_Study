package springstudy.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springstudy.springtest.repository.*;
import springstudy.springtest.service.MemberService;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

//    private DataSource dataSource;

    private final MemberRepository memberRepository;

    private EntityManager em;

    @Autowired
    public SpringConfig(MemberRepository memberRepository, EntityManager em) {
        this.memberRepository = memberRepository;
        this.em = em;
    }
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);//리포지토리를넣어줘야해서 에러가발생한다.
    }

//    @Bean
//    public MemberRepository memberRepository() {
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
//        //이걸로 바꿔서 메모리에 저장되던것을 바꿔줘야한다 스프링에서 빈연결해주는 부분이다.
////        return new JpaMemberRepository(em);
//    }

}
