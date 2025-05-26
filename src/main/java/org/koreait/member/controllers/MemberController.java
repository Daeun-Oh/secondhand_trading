package org.koreait.member.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("addCss", List.of("member/style.css", "member/test.css"));
        model.addAttribute("addScript", List.of("member/form", "member/test2"));

        model.addAttribute("addCommonCss", List.of("test/common1", "test/common2"));
        model.addAttribute("addCommonScript", List.of("fileManager"));
        model.addAttribute("pageTitle", "회원가입");    // 페이지 타이틀 설정

        return "front/member/join";
    }
}
