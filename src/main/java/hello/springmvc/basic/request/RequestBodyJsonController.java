package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class RequestBodyJsonController {

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * * {"username":"hello", "age":20}
     * * content-type: application/json
     * HttpServletRequest를 사용해서 직접 HTTP 메시지 바디에서 데이터를 읽어와서, 문자로 변환한다.
     * 문자로 된 JSON 데이터를 Jackson 라이브러리인 `objectMapper` 를 사용해서 자바 객체로 변환한다.
     */
    @PostMapping("request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String bodyMessage = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        HelloData helloData = objectMapper.readValue(bodyMessage, HelloData.class);

        log.info("info log : bodyMessage = {}", bodyMessage);
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("hello");
    }


    /**
     * bodyMessage를 바로 읽어와 활용할 수 있다.
     * Objectmapper를 사용해서 객체로 전환할 수 있다.
     */
    @PostMapping("request-body-json-v2")
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String bodyMessage) throws JsonProcessingException {

        HelloData helloData = objectMapper.readValue(bodyMessage, HelloData.class);
        log.info("info log : bodyMessage = {}", bodyMessage);
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "hello";
    }

    /**
     * *@RequestBody 객체 파라미터**
     * `@RequestBody HelloData data`
     * `@RequestBody` 에 직접 만든 객체를 지정할 수 있다.
     *
     * @RequestBody를 생략하면 @ModelAttribute가 적용되어 요청파라미터를 조회하는 데 없으니까 값을 잃어버린다.
     */
    @PostMapping("request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "helloV3";
    }

    /**
     * HttpEntity제네릭 타입을 지정하면 우리가 받고자 하는 객체로 데이터를 받을 수 있다.
     */
    @PostMapping("request-body-json-v4")
    @ResponseBody
    public String requestBodyJsonV4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        assert helloData != null;
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "helloV4";
    }


    /**
     * HTTP 메시지 컨버터가 Json형식 데이터를 수신할 때도 발신할 때도 객체로 변환하는 역할을 수행해준다.
     * @ResposeBody를 적용한 메서드의 반환형이 객체형태로 반환하면 json데이터를 반환할 수 있다.
     * `@RequestBody` 요청
     * JSON 요청  -> HTTP 메시지 컨버터 -> 객체
     *
     * `@ResponseBody` 응답
     * 객체 ->  HTTP 메시지 컨버터 ->  JSON 응답
     */
    @PostMapping("request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return helloData;
    }
}
