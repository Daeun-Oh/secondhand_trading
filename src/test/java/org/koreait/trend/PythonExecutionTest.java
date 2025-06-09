package org.koreait.trend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;

@SpringBootTest
public class PythonExecutionTest {

    @Test
    void test1() throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                "C:/Users/admin/Desktop/Daeun-ai/08 Data-Analysis-Python/source/basic/Scripts/activate.bat"); // 가상환경 활성화
        Process process = builder.start();     // 명령어 실행
        int statusCode = process.waitFor();    // 0: 정상 실행, 1: 비정상 종료
        if (statusCode != 0) {
            process.inputReader().lines().forEach(System.out::println);
            return;           // 정상 실행이 아닌 경우 다음 실행 X
        }

        builder = new ProcessBuilder(
                "C:/Users/admin/Desktop/Daeun-ai/08 Data-Analysis-Python/source/basic/Scripts/python.exe",
                "C:/Users/admin/Desktop/Daeun-ai/08 Data-Analysis-Python/source/uploads/trend/trend.py",
                "C:/Users/admin/Desktop/Daeun-ai/08 Data-Analysis-Python/source/uploads/trend");
        process = builder.start();
        statusCode = process.waitFor();
        System.out.println("statusCode:" + statusCode);
        BufferedReader br = process.errorReader();
        br.lines().forEach(System.out::println);
    }
}
