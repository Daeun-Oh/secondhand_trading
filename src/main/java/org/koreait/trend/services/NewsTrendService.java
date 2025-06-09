package org.koreait.trend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.configs.FileProperties;
import org.koreait.global.configs.PythonProperties;
import org.koreait.trend.entities.NewsTrend;
import org.koreait.trend.entities.Trend;
import org.koreait.trend.repositories.TrendRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Lazy  // 항상 쓰는 게 아니니까 Lazy 사용
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({PythonProperties.class, FileProperties.class})
public class NewsTrendService {
//    @Value("${python.path.base}")
//    private String pythonDir;  -> 굳이 이렇게 안 쓰고, PythonProperties에서 주입 받는다.
    private final PythonProperties pythonProperties;
    private final FileProperties fileProperties;

    private final WebApplicationContext ctx;
    private final TrendRepository repository;
    private final HttpServletRequest request;
    private final ObjectMapper om;

    // 해야 할 일: 주소를 매개변수로 받아야 함
    public NewsTrend process() {
        // 환경변수 설정에서 spring.profiles.active=default,prod -> 이 중에 prod인지 확인하는 코드
        boolean isProduction = Arrays.stream(ctx.getEnvironment().getActiveProfiles())
                .anyMatch(s -> s.equals("prod"));

        String activationCommand = null, pythonPath = null;
        if (isProduction) {     // 리눅스 환경, 서비스 환경
            activationCommand = String.format("source %s/activate", pythonProperties.getBase());
            pythonPath = pythonProperties.getBase() + "/python";
        } else {    // 윈도우즈 환경
            activationCommand = String.format("%s/activate.bat", pythonProperties.getBase());
            pythonPath = pythonProperties.getBase() + "/python.exe";
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(activationCommand);  // 가상환경 활성화
            Process process = builder.start();
            if (process.waitFor() == 0) {  // 0: 정상 수행된 경우
                builder = new ProcessBuilder(pythonPath, pythonProperties.getTrend() + "trend.py", fileProperties.getPath() + "/trend");
                process = builder.start();
                int statusCode = process.waitFor();
                if (statusCode == 0) {
                    // 이때는 데이터를 가져와서 출력
                    String json = process.inputReader().lines().collect(Collectors.joining());
//                    System.out.println("json:" + json);
                    return om.readValue(json, NewsTrend.class);  // NewsTrend 객체
                } else {
                    // 오류 확인
//                    System.out.println("statusCode:" + statusCode);
//                    process.errorReader().lines().forEach(System.out::println);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 매 1시간마다 주기적으로 뉴스 트렌드 데이터를 저장
     */
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.HOURS)  // 활성화 필요 -> MvcConfig에서 @EnableScheduling
    public void scheduledJob() {
        NewsTrend item = process();
        if (item == null) return;

        String wordCloud = String.format("%s%s/trend/%s", request.getContextPath(), fileProperties.getUrl(), item.getImage());

        try {
            String keywords = om.writeValueAsString(item.getKeywords());
            Trend data = new Trend();
            data.setCategory("NEWS");
            data.setWordCloud(wordCloud);
            data.setKeywords(keywords);
            repository.save(data);
        } catch (JsonProcessingException e) {

        }
    }
}
