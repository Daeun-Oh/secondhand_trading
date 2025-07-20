//package org.koreait.test;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class Ex01 {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper om;
//
//    @Test
//    void test1() throws Exception {
//        RequestTest form = new RequestTest();
//        String json = om.writeValueAsString(form);
//
//        mockMvc.perform(post("/test2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json).with(csrf().asHeader()))
//                .andDo(print());
//
//        /**
//         * 출력 결과
//         *
//         * MockHttpServletRequest:
//         *       HTTP Method = POST
//         *       Request URI = /test2
//         *        Parameters = {}
//         *           Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"58"]
//         *              Body = {"param1":null,"param2":null,"param3":null,"param4":false}
//         *     Session Attrs = {org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN=org.springframework.security.web.csrf.DefaultCsrfToken@476dc0af}
//         *
//         * ...
//         *
//         * MockHttpServletResponse:
//         *            Status = 401
//         *     Error message = null
//         *           Headers = [X-Content-Type-Options:"nosniff", X-XSS-Protection:"0", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"SAMEORIGIN"]
//         *      Content type = null
//         *              Body =
//         *     Forwarded URL = null
//         *    Redirected URL = null
//         *           Cookies = []
//         *
//         * -> 현재 요청이 차단되었다.
//         * -> CSRF 토큰 작동 (보안)
//         *    : 세션에 저장된 값과 일치하는지 확인. 일치하면 정상 요청, 불일치하면 공격으로 간주하여 요청을 차단!
//         * -> 해결책: 토큰을 양식 or 헤더에 실어서(포함시켜) 보낸다. - .with(csrf()), .with(csrf().asHeader())
//         *    https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html
//         */
//
//        /**
//         * 출력 결과
//         * MockHttpServletRequest:
//         *       HTTP Method = POST
//         *       Request URI = /test2
//         *        Parameters = {}
//         *           Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"58", X-CSRF-TOKEN:"khWlgBI4Lv9X_Fx_T6qFjJcRa8m30ofRdtKLfGvzFdIlRvzuoyeXtyEOSsZ6z28bdoex6aUgRqjV4bH8F7PtTVjBJ7dHfsjb"]
//         *              Body = {"param1":null,"param2":null,"param3":null,"param4":false}
//         *     Session Attrs = {}
//         *
//         * ...
//         *
//         * MockHttpServletResponse:
//         *            Status = 400
//         *     Error message = null
//         *           Headers = [Content-Type:"application/json", X-Content-Type-Options:"nosniff", X-XSS-Protection:"0", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"SAMEORIGIN"]
//         *      Content type = application/json
//         *              Body = {"status":"BAD_REQUEST","messages":{"param3":["NotBlank.requestTest.param3","NotBlank.param3","NotBlank.java.lang.String","필수 입력 항목입니다."],"param4":["AssertTrue.requestTest.param4","AssertTrue.param4","AssertTrue.boolean","AssertTrue"],"param1":["NotBlank.requestTest.param1","NotBlank.param1","NotBlank.java.lang.String","필수 입력 항목입니다."],"param2":["NotBlank.requestTest.param2","NotBlank.param2","NotBlank.java.lang.String","필수 입력 항목입니다."]}}
//         *     Forwarded URL = null
//         *    Redirected URL = null
//         *           Cookies = []
//         *
//         * -> 문제점: Body의 messages에 코드까지 있다
//         */
//
//        /**
//         * 출력 결과
//         *
//         * MockHttpServletRequest:
//         *       HTTP Method = POST
//         *       Request URI = /test2
//         *        Parameters = {}
//         *           Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"58", X-CSRF-TOKEN:"09Hk4jzZ8ELpc131npK68w7cukLawbUsw0QLan9PRI9qwqbG6rDRgFnowSfEFmiX-7-Oy2jul3q5-dYBoHQyDEh2IetfoZ72"]
//         *              Body = {"param1":null,"param2":null,"param3":null,"param4":false}
//         *     Session Attrs = {}
//         *
//         * ...
//         *
//         * MockHttpServletResponse:
//         *            Status = 400
//         *     Error message = null
//         *           Headers = [Content-Type:"application/json", X-Content-Type-Options:"nosniff", X-XSS-Protection:"0", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"SAMEORIGIN"]
//         *      Content type = application/json
//         *              Body = {"status":"BAD_REQUEST","messages":{"param3":["필수 입력 항목입니다."],"param4":[],"param1":["필수 입력 항목입니다."],"param2":["필수 입력 항목입니다."]}}
//         *     Forwarded URL = null
//         *    Redirected URL = null
//         *           Cookies = []
//         */
//    }
//}
