package org.koreait.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "python.path") // 항상 application.yml의 python.path의 하위 변수에 주입
public class PythonProperties {
    private String base;    // 파이썬 설치 경로
    private String trend;   // 뉴스 트렌드 py 파일 경로
    private String base2;
    private String restaurant;
}
