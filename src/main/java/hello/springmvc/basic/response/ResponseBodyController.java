package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Controller
@Slf4j
public class ResponseBodyController {

    @GetMapping("/response-body-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        log.info("log info : responseBodyV1");
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-v2")
    public ResponseEntity<String> responseBodyV1() {
        log.info("log info : responseBodyV2");
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-v3")
    public String responseBodyV3() {
        log.info("log info : responseBodyV3");
        return "hello";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        log.info("log info : responseBodyJsonV1");

        HelloData helloData = new HelloData();
        helloData.setUsername("lee");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK) //반환형이 객체인 걍우 응답 코드를 설정할 수 없으니 애노테이션으로 제공한다
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        log.info("log info : responseBodyJsonV2");

        HelloData helloData = new HelloData();
        helloData.setUsername("lee");
        helloData.setAge(20);
        return helloData;
    }
}
