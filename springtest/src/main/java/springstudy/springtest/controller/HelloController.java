package springstudy.springtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")//map app에서 hello라고 들어오면 여기서 해준다.
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }
}
