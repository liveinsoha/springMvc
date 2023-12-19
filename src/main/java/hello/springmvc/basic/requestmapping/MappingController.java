package hello.springmvc.basic.requestmapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@Slf4j //필드 선언 없이 log를 사용할 수 있다.
public class MappingController {


    //메소드 속성이 명시되지 않으 경우 모든 형태의 메소드를 다 받을 수 있다.
    @RequestMapping(value = {"/hello-basic", "/hello-basic2"}) //배열 {}형태로 다중 매핑이 가능하다.
    public String helloBasic() {
        log.info("info log={}", "helloBasic");
        return "ok";
    }

    @RequestMapping(value = {"/hello-get", "/hello-get2"}, method = RequestMethod.GET) //배열 {}형태로 다중 매핑이 가능하다.
    public String helloBasic2() {
        log.info("info log={}", "helloBasic2");
        return "ok";
    }

    @GetMapping(value = {"/hello-get-v2", "/hello-get2-v2"}) //배열 {}형태로 다중 매핑이 가능하다.
    public String helloGet() {
        log.info("info log={}", "helloGet");
        return "ok";
    }


    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     *
     * @PathVariable("userId") String userId -> @PathVariable String userId
     * url자체에 값이 들어가 있는 경우. 경로변수라고 칭하고 @PathVariable애노테이션을 사용하여 파라미터로 받을 수 있다
     */
    @GetMapping("/hello-basic/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        String messageBody = "ID = " + data;
        log.info("mappingPath userId = {}", data);
        return messageBody;
    }

    @GetMapping("/hello-basic/{username}/{userId}") //경로변수와 파라미터명이 같으면 애노테이션의 속성을 생략할 수 있다.
    public String mappingPath2(@PathVariable String username, @PathVariable String userId) {
        String messageBody = "username = " + username + " " + "userId = " + userId;
        log.info("mappingPath2 username = {}, userId = {}", username, userId);
        return messageBody;
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId); //로그 파라미터 두개 넣기
        return "ok";
    }

    /** 세가지 구분하기
     * 경로변수로 넘어오는
     * get 요청 url의 쿼리파라미터로 넘어오는
     * post요청 메시지 바디
     */

    /**
     * 특정 파라미터 조건 매핑!
     * 특정 파라미터 값이 있거나, 특정 파라미터의 값이 무엇이거나 해야만 매핑이 가능하다!
     * 여러 조건의 사용이 가능하다
     * * params="mode",
     * * params="!mode"
     * * params="mode=debug"
     * * params="mode!=debug" (! = )
     * * params
     */
    @GetMapping(value = "/mapping-param", params = "mode")
    public String mappingParam(@RequestParam("mode") String mode) {
        log.info("info log : mode =  {}", mode);
        return "ok";
    }

    /**
     * HTTP요청의 헤더가 특정 조건을 만족하였을 떄만 매핑이 가능하도록 설정할 수 있다.
     * 위의 파라미터 매핑과 비슷하다. 둘 다 별로 쓸일은 없다.
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */

    @GetMapping(value = "/mapping-header", headers = "mode=debug") //header에 key-value형태로 mode = debug가 설정 되어 있어야만 호출
    public String mappingHeader() {
        log.info("log info = {}", "mappingHeader");
        return "ok";
    }

    /**
     * HTTP 요청의 Content-Type헤더의 값을 기반으로 매핑한다
     * consumes = "text/plain"
     * consumes = {"text/plain", "application/*"}
     * consumes = MediaType.TEXT_PLAIN_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE) //Content-Type 헤더 값이 application/json이어야 함
    public String mappingConsume() {
        log.info("log info = {}", "mappingConsume");
        return "ok";
    }

    /**
     * 서버의 입장에서 produce생산 하는 타입이 text/html이므로
     * 클라이언트 입장에서 Accept(받아들일 수 있는 타입)헤더의 정보가 text/html일 떄만 매핑이 가능하다.
     * Accept : text/html 이라는 것은 클라이언트(브라우저)입장에서 받아들일 수 있는 타입이 제한되어 있다는 이야기이다.
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE) //스프링에서 지정해 놓은 상수를 사용하는 게 낫다
    public String mappingProduce() {
        log.info("log info = {}", "mappingProduce");
        return "ok";
    }

}
