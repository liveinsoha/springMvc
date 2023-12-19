package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

//컨트롤러는 헤더정보를 파라미터로 받아 처리할 수도 있다.
@Slf4j
@RestController
public class RequestHeaderController {
    /**
     *  애노테이션 기반 컨트롤러는 다양한 파라미터를 지원해준다.
     *
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String>
                                  headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false)
                          String cookie
    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }

    /**
     * `MultiValueMap`
     * MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
     * HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.
     * **keyA=value1&keyA=value2**
     * MultiValueMap<String, String> map = new LinkedMultiValueMap();
     * map.add("keyA", "value1");
     * map.add("keyA", "value2");
     * //[value1,value2]
     * List<String> values = map.get("keyA");
     */
}
