package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamsController {

    //리턴 타입이 void일 수도 있다. 다양한 메서드 형태를 지원
    @RequestMapping("/request-param-v1") //Get으로 오든 Post로 오든 파라미터 조회가 가능하다
    public void requestParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("log info : getParams :  username = {}, age = {}", username, age);

        PrintWriter writer = response.getWriter();
        writer.write("ok"); //response에 직접 쓸 수 있다.
    }

    @RequestMapping("/request-param-v2")
    @ResponseBody
    /**
     * @RequestParam을 이용해서 파라미터를 받을 수 있고 파라미터 명이랑 같다면 애노테이션의 value속성을 생략할 수 있다.
     * 클래스 레벨의 @Controller애노테이션을 붙으면 String을 리턴하는 메서드의 경우 뷰를 찾게 된다.
     * 이떄 메서드 레벨에서 @ResponseBody 애노테이션을 붙이면 String리턴 시 뷰를 찾지않고 HTTP message Body에 직접 입력한다.
     */
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info(" log info : requestParamsV2 : username = {}, age = {}", memberName, memberAge);
        return "ok";
    }

    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info(" log info : requestParamsV2 : username = {}, age = {}", username, age);
        return "ok";
    }


    /**
     * age를 꼭 입력하지 않아도 될 경우에 required = false를 쓰면 되는데 값이 없는 경우 int 에 null을 대입할 수 없으므로
     * age의 타입을 Integer로 두어야 한다 (int로 그냥 두고 입력하지 않을경우 에러코드는 500, 서버의 오류)
     * 혹은 값이 없을 경우 기본값을 설정해주는 defaultValue속성을 이용해서 null대신 기본 정수를 대입할 수 있도록 한다,
     * required가 설정 되면 HTTP request에 파라미터가 반드시 있어야 하는데, 이를 지키지 않은 경우 에러코드는 400이다. 클라이언트의 잘못.
     */
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(@RequestParam(required = true) String username,
                                 @RequestParam(required = false) Integer age) {
        log.info(" log info : requestParamsV2 : username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * defaultValue` 는 빈 문자의 경우에도 설정한 기본 값이 적용된다.
     * defaultValue 속성이 있는 경우 required는 의미가 없다. 이미 기본값이 저장되어 있기 떄문.
     * 빈 문자열이 속성값으로 들어온 경우에도 기본값이 적용된다.
     */
    @RequestMapping("/request-param-v5")
    @ResponseBody
    public String requestParamV5(@RequestParam(required = true, defaultValue = "guest") String username,
                                 @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info(" log info : requestParamsV2 : username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * 하나의 파라미터에 값을 여러 개 적은 경우 /request-param-v6?username=lee&age=13&age=23
     * MultiValueMap을 이용하면 모두 조회할 수 있다.
     * log info : requestParamsV2 : username = [lee], age = [13, 23]
     */

    @RequestMapping("/request-param-v6")
    @ResponseBody
    public String requestParamV6(@RequestParam MultiValueMap<String, Object> paramMap) {
        log.info(" log info : requestParamsV2 : username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }


    /**
     * 스프링MVC는 `@ModelAttribute` 가 있으면 다음을 실행한다.
     * `HelloData` 객체를 생성한다.
     * 요청 파라미터의 이름으로 `HelloData` 객체의 프로퍼티를 찾는다. 프로퍼티란 게터 세터등을 의미.
     * 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩) 한다.
     * 예) 파라미터 이름이 `username` 이면 `setUsername()` 메서드를 찾아서 호출하면서 값을 입력한다.
     * @ModelAttribute 생략 가능하다..
     */
    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("info log : username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
