package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "add user";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete user " + userId;
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "Get user : " + userId;
    }

    @PatchMapping("/{userId}")
    public String update(@PathVariable String userId) {
        return "update user : " + userId;
    }
}
