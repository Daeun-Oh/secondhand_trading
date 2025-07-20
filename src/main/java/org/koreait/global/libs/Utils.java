package org.koreait.global.libs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.FileInfoService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    private final FileInfoService infoService;

    /**
     * CSS, JS 버전
     *
     * @return
     */
    public int version() {
        return 1;
    }

    public String keywords() {
        return "";
    }

    public String description() {
        return "";
    }

    /**
     * 휴대폰 장비인지, PC인지
     *
     * @return
     */
    public boolean isMobile() {
        String ua = request.getHeader("User-Agent");
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return StringUtils.hasText(ua) && ua.matches(pattern);
    }

    /**
     * mobile, front 템플릿을 분리
     *
     * @param path
     * @return
     */
    public String tpl(String path) {
        String prefix = isMobile() ? "mobile" : "front";

        return String.format("%s/%s", prefix, path);
    }

    /**
     * 메세지를 코드로 조회
     *
     * @param code
     * @return
     */
    public String getMessage(String code) {
        Locale locale = localeResolver.resolveLocale(request);

        return messageSource.getMessage(code, null, locale);
    }

    /**
     * code 배열을 받아 List로 변환
     *
     * @param codes
     * @return
     */
    public List<String> getMessages(String[] codes) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        try {
            return Arrays.stream(codes)
                    .map(c -> {
                        try {
                            return getMessage(c);
                        } catch (Exception e) {}
                        return "";
                    }).filter(s -> !s.isBlank()).toList();
        } finally {
            ms.setUseCodeAsDefaultMessage(true);
        }
    }

    /**
     * 커맨드 객체 검증 실패 메시지 처리 (REST)
     * 커맨드 객체 -> JSON 문자열 변환
     *
     * @param errors
     * @return
     */
    public Map<String, List<String>> getErrorMessages(Errors errors) {
        // 필드별 검증 실패 메시지 - rejectValue, 커맨드 객체 검증(필드)
        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        f -> getMessages(f.getCodes()),
                        (v1, v2) -> v2));  // toMap: key-value 형태. key가 이미 있는데 value를 재할당하려고 하면 오류를 발생 시킴 (기존 key 값을 보호하기 위해) -> 3번째 매개변수로 "value 할당 기준"을 설정해 주면 괜찮음

        // 글로벌 검증 실패 메시지 - reject
        List<String> gMessages = errors.getGlobalErrors().stream().flatMap(g -> getMessages(g.getCodes()).stream()).toList();

        if (!gMessages.isEmpty()) {
            messages.put("global", gMessages);
        }

        return messages;
    }

    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * Thumbnail 이미지를 템플릿에서 출력하는 함수
     *
     * @param seq : 파일등록번호
     * @param width : 너비
     * @param height : 높이
     * @param crop : 크롭 여부
     * @return
     */
    public String printThumb(Long seq, int width, int height, String addClass, boolean crop) {
        String url = null;
        try {
            FileInfo item = infoService.get(seq);
            long folder = seq % 10L;
            url = String.format("%s/file/thumb?seq=%s&width=%s&height=%s&crop=true", request.getContextPath(), seq, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        url = StringUtils.hasText(url) ? url : request.getContextPath() + "/common/images/no_image.jpg";

        return String.format("<img src='%s' class='%s%s'>", url, "image-" + seq,StringUtils.hasText(addClass)? " " + addClass : "");
    }

    public String printThumb(Long seq, int width, int height, String addClass) {
        return printThumb(seq, width, height, addClass, true);
    }

    public String printThumb(Long seq, int width, int height) {
        return printThumb(seq, width, height, null);
    }

    public String printThumb(Long seq) {
        return printThumb(seq, 100, 100);
    }


    /**
     * 이미지가 없는 경우 출력하는 이미지
     * @return
     */
    public String printNoImage() {
        String url = request.getContextPath() + "/common/images/no_image.jpg";

        return String.format("<img src='%s'>", url);
    }

    /**
     * 전체 주소: https://site123.com:4000/member/...
     * @param url
     * @return
     */
    public String getUrl(String url) {
        String protocol = request.getScheme(); // http, https, ftp, ...
        String domain = request.getServerName();
        int _port = request.getServerPort();
        String port =  List.of(80, 443).contains(_port) ? "" : ":" + _port;

        return String.format("%s://%s%s%s%s", protocol, domain, port, request.getContextPath(), url);
    }
}