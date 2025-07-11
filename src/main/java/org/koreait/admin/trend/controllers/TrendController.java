package org.koreait.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.controllers.CommonController;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.services.TrendInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController extends CommonController {

    private final TrendInfoService infoService;

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "trend";
    }

    /**
     * 메인 페이지
     * To-do : news, etc 페이지 구현
     *
     * @return
     */
    @GetMapping({"", "/news"})  // 주소: /admin/trend, /admin/trend/news
    public String news(Model model) {
        commonProcess("news", model);

        Trend item = infoService.getLatest("NEWS");
        model.addAttribute("item", item);

        return "admin/trend/news";
    }

    @GetMapping("/etc")
    public String etc(@ModelAttribute TrendSearch search, Model model) {
        commonProcess("etc", model);
        return "admin/trend/etc";
    }

    /**
     * 공통 처리
     *
     * @param code : 서브메뉴 코드
     * @param model
     */
    private void commonProcess(String code, Model model) {
        code = StringUtils.hasText(code) ? code : "news";  // code의 기본값 설정

        String pageTitle = "";
        //List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        //addCommonScript.add("chart/chart"); // 공통

        if (code.equals("news")) {
            addScript.add("trend/news");
            pageTitle = "오늘의 뉴스 트렌드";

        } else if (code.equals("etc")) {
            // To-do
        }

        model.addAttribute("subCode", code);
        //model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }
}
