package org.koreait.member.social.services;

public interface SocialLoginService {
    // 발급받은 토큰 확인
    String getToken(String code);

    // 해당 토큰으로 로그인
    boolean login(String token);

    // 토큰이 존재하는가?
    boolean exists(String token);

    //
    String getLoginUrl(String redirectUrl);
}
