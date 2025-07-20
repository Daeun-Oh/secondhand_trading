package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.koreait.member.social.entities.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@SpringBootTest
public class AccessTokenTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "4220a05432155c126bdb2dd8af8e4745");
        body.add("redirect_uri", "http://localhost:4000/member/social/callback/kakao");
        body.add("code", "kUGtyyfmf9s7i74XJ7j9iaASBsGTIGo-yKKIrsA7BXBoDSMkG3HVGwAAAAQKFxafAAABmBusT1iUJG13ldIf8A");
        // code -> 일정 시간마다 바꿔줘야 함.

        // HttpEntity: 요청 전문에 대한 상세 설정을 위해 사용
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        String requestUrl = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<AuthToken> response = restTemplate.exchange(
                URI.create(requestUrl),
                HttpMethod.POST,
                request,
                AuthToken.class
        );
        HttpStatusCode statusCode = response.getStatusCode();

        System.out.println("statusCode: " + statusCode);
        System.out.println("response body: " + response.getBody());

        // access Token(=정보교환권)으로 회원정보 조회
        AuthToken authToken = response.getBody();
        requestUrl = "https://kapi.kakao.com/v2/user/me";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(authToken.getAccessToken());

        // ResponseEntity: 응답 헤더에 대한 상세 설정을 위해 사용
        request = new HttpEntity<>(headers);
        ResponseEntity<Map> res = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, Map.class);

        //System.out.println("response body 2: " + res.getBody());

        Map resBody = res.getBody();
        long id = (long)resBody.get("id");

        System.out.println("id: " + id);
    }
}
