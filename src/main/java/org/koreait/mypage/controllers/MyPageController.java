package org.koreait.mypage.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.libs.Utils;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.survey.diabetes.services.DiabetesSurveyInfoService;
import org.koreait.survey.entities.DiabetesSurvey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final Utils utils;
    private final HttpServletRequest request;
    private final DiabetesSurveyInfoService infoService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        commonProcess("user", model);

        return utils.tpl("mypage/index");
    }

    @GetMapping("/survey/diabetes")
    public String result(Model model) {
        commonProcess("user", model);

        ListData<DiabetesSurvey> data = infoService.getList(new CommonSearch());

        System.out.println("data: " + data);


        model.addAttribute("data", data);

        return utils.tpl("mypage/survey/diabetes/list");
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "user";  // 기본값 설정
        String pageTitle = "";
        if (mode.equals("user")) {
            pageTitle = utils.getMessage("나의_당뇨_고위험군_테스트_결과");
        } else if (mode.equals("admin")) {
            pageTitle = utils.getMessage("모든_회원_당뇨_고위험군_테스트_결과");
        }

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("pageTitle", pageTitle);

    }
}
