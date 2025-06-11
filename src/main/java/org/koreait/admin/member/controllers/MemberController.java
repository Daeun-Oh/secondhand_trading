package org.koreait.admin.member.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.koreait.global.search.ListData;
import org.koreait.member.constants.Authority;
import org.koreait.member.controllers.MemberSearch;
import org.koreait.member.entities.Member;
import org.koreait.member.services.MemberInfoService;
import org.koreait.member.services.MemberUpdateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("adminMemberController")  // memberController라는 이름의 Bean이 2개라 오류 뜸 -> 컨트롤러 Bean 이름 지정
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController extends CommonController {

    private final MemberInfoService infoService;
    private final MemberUpdateService updateService;

    @ModelAttribute("authorities")
    public Authority[] authorities() {
        return Authority.values();
    }

    @Override
    public String mainCode() {
        return "member";
    }

    @GetMapping({"", "/list"})
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = infoService.getList(search);
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "admin/member/list";
    }

    @RequestMapping({"", "list"})
    public String listPs(@RequestParam(name="chk", required = false) List<Integer> chks, Model model) {

        updateService.processBatch(chks);

        // 처리 완료 후에는 부모 창의 목록을 새로고침
        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 컨트롤러 공통 처리
     *
     * @param code
     * @param model
     */
    private void commonProcess(String code, Model model) {
        code = StringUtils.hasText(code) ? code : "list";
        String pageTitle = "";

        if (code.equals("")) {
            pageTitle = "회원목록";
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subCode", code);
    }
}
