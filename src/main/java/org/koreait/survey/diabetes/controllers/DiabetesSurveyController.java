package org.koreait.survey.diabetes.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.global.constants.Gender;
import org.koreait.global.libs.Utils;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.services.DiabetesSurveyInfoService;
import org.koreait.survey.diabetes.services.DiabetesSurveyService;
import org.koreait.survey.entities.DiabetesSurvey;
import org.koreait.survey.validators.DiabetesSurveyValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/survey/diabetes")
@SessionAttributes("requestDiabetesSurvey")
public class DiabetesSurveyController {

    private final HttpServletRequest request;
    private final Utils utils;
    private final DiabetesSurveyValidator validator;
    private final DiabetesSurveyService service;
    private final DiabetesSurveyInfoService infoService;

    @ModelAttribute("addCss")
    public List<String> addCss() {
        return List.of("survey/diabetes/style");
    }

    // 설문 데이터를 여러 요청 간에 유지하기 위해 세션에 저장
    @ModelAttribute("requestDiabetesSurvey")
    public RequestDiabetesSurvey requestDiabetesSurvey() {
        // form 기본값 설정
        RequestDiabetesSurvey form = new RequestDiabetesSurvey();
        form.setGender(Gender.FEMALE);
        form.setSmokingHistory(SmokingHistory.CURRENT);

        return form;
    }

    // Gender enum 배열을 step1.html에서 model attribute로 사용 가능하게 등록 -> ${genders}
    @ModelAttribute("genders")
    public Gender[] genders() {
        return Gender.values();
    }

    // SmokingHistory enum 배열을 step1.html에서 model attribute로 사용 가능하게 등록 -> ${smokingHistories}
    @ModelAttribute("smokingHistories")
    public SmokingHistory[] smokingHistories() {
        return SmokingHistory.values();
    }

    @GetMapping({"", "/"})
    public String main(Model model) {
        commonProcess("main", model);

        return utils.tpl("survey/diabetes/main");
    }

    @GetMapping("/step1")
    public String step1(@ModelAttribute RequestDiabetesSurvey form, Model model) {
        commonProcess("step", model);



        return utils.tpl("survey/diabetes/step1");
    }

    @PostMapping("/step2")
    public String step2(@Valid RequestDiabetesSurvey form, Errors errors, Model model) {
        commonProcess("step", model);

        validator.validate(form, errors);  // step1에 대한 검증

        if (errors.hasErrors()) {
            return utils.tpl("survey/diabetes/step1");
        }

        return utils.tpl("survey/diabetes/step2");
    }

    /**
     * 설문 저장(DB) 및 결과 처리
     *
     * @param form
     * @param errors
     * @return
     */
    @PostMapping("/process")
    public String process(@Valid RequestDiabetesSurvey form, Errors errors, Model model, SessionStatus status) {
        commonProcess("step", model);

        validator.validate(form, errors);  // step2에 대한 검증

        if (errors.hasErrors()) {
            return utils.tpl("survey/diabetes/step2");
        }

        // 설문 결과 및 저장 처리 (DB)
        DiabetesSurvey item = service.process(form);

        // 세션에 바인딩된 모델(@SessionAttributes)을 더 이상 유지하지 않도록 제거
        status.setComplete();

        // 양식데이터 초기화
        model.addAttribute("requestDiabetesSurvey", requestDiabetesSurvey());

        return "redirect:/survey/diabetes/result/" + item.getSeq();
    }

    /**
     * 설문 결과 보기
     *
     * @param seq 설문 번호
     * @return
     */
    @GetMapping("/result/{seq}")
    public String result(@PathVariable("seq") Long seq, Model model) {
        commonProcess("result", model);

        DiabetesSurvey item = infoService.get(seq);
        model.addAttribute("item", item);

        ListData<DiabetesSurvey> data = infoService.getList(new CommonSearch());
        System.out.println("data: " + data);

        return utils.tpl("survey/diabetes/result");
    }

    /**
     * 컨트롤러 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "step";  // 기본값 설정
        String pageTitle = "";
        if (mode.equals("main") || mode.equals("step")) {
            pageTitle = utils.getMessage("당뇨_고위험군_테스트");
        } else if (mode.equals("result")) {
            pageTitle = utils.getMessage("당뇨_고위험군_테스트_결과");
        }

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("pageTitle", pageTitle);

    }
}
