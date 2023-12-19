package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * `@Controller` 는 반환 값이 `String` 이면 뷰 이름으로 인식된다. 그래서 **뷰를 찾고 뷰가 랜더링** 된다.
 * `@RestController` 는 반환 값으로 뷰를 찾는 것이 아니라, **HTTP 메시지 바디에 바로 입력**한다. 따라서
 * 실행 결과로 ok 메세지를 받을 수 있다. `@ResponseBody` 와 관련이 있는데, 뒤에서 더 자세히 설명한다.
 */

// 실제 자바 코드를 건드리지 않고 설정만으로 어느 레벨에서 출력할지 결정할 수 있다!
// 로그를 사용하면 콘솔 뿐만 아니라 파일로 또는 네트워크로 전송할 수도 있다.
@RestController //RestController는 반환이 String이면 뷰이름을 찾는 대신 http response응답 바디에 문자열을 게재한다
//@Slf4j
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    //직접 필드를 선언 해도 되지만 @Slf4j를 사용하면 자동으로 log필드를 쓸 수 있다.

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "spring";
        System.out.println("name = " + name);

        /**
         * LEVEL: `TRACE > DEBUG > INFO > WARN > ERROR`
         * 개발 서버는 debug 출력
         * 운영 서버는 info 출력
         */

        log.debug("debug log = "  + name );
/**
 * log.debug("data="+data)`
 * 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다. 결과적으
 * 로 문자 더하기 연산이 발생한다.
 * `log.debug("data={}", data)`
 * 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이 발생하지
 * 않는다.
 */
        log.trace(" trace log={}", name);
        log.debug(" debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error(" error log={}", name);
        return "ok";
    }


}

/**
 * 2023-12-19T15:36:57.122+09:00  INFO 26900 --- [nio-8080-exec-4] h.springmvc.basic.LogTestController      :  info log=spring
 * 시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지
 */

/**
 * 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
 * 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게
 * 조절할 수 있다.
 * 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파
 * 일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.
 * 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한
 * 다.
 */
