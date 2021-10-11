package springstudy.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springstudy.springtest.domain.Member;
import springstudy.springtest.service.MemberService;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";//멤버스의 createForm이라는 곳으로 이동한다.
        //template에서 찾아서 알아서 렌더링해준다.
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {//memberform에 name에 spring이라는 입력된값이 들어오게된다.
        Member member = new Member();
        member.setName(form.getName());
        //여기에 null이 들어간다?
        System.out.println(member.getName());
        //이렇게하면 member가 만들어진다.

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
