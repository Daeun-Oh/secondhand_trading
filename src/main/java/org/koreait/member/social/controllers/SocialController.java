package org.koreait.member.social.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyCommonController;
import org.koreait.member.social.constants.SocialType;
import org.koreait.member.social.services.KakaoLoginService;
import org.koreait.member.social.services.NaverLoginService;
import org.koreait.member.social.services.SocialLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ApplyCommonController
@RequiredArgsConstructor
@RequestMapping("/member/social")
public class SocialController {

    private final HttpSession session;
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;

    @GetMapping("/callback/{channel}")
    public String callback(@PathVariable("channel") String type,
                           @RequestParam("code") String code,
                           @RequestParam(name = "state", required = false) String redirectUrl) {

        SocialType socialType = SocialType.valueOf(type.toUpperCase());

        // social type에 따른 서비스 설정
        SocialLoginService service = null;
        switch (socialType) {
            case KAKAO -> service = kakaoLoginService;
            case NAVER -> service = naverLoginService;
        }

        System.out.println("social type " + socialType);

        // 토큰 발급
        String token = service.getToken(code);

        /* 로그인 처리 S */
        if (service.login(token)) {
            // 로그인 성공 시: redirectUrl 또는 메인페이지 이동
            return "redirect:" + (StringUtils.hasText(redirectUrl) ? redirectUrl : "/");
        }
        /* 로그인 처리 E */

        // 로그인 실패 시: 회원가입으로 이동
        // 사용자가 다른 페이지로 이동하거나 다음 요청을 할 때, 서버는 세션을 통해 이전 정보를 유지
        session.setAttribute("socialType", socialType);
        session.setAttribute("socialToken", token);

        return "redirect:/member/join";
    }
}
