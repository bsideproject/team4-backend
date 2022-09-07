package com.bside.sidefriends.security;

import com.bside.sidefriends.security.auth.LoginUser;
import com.bside.sidefriends.users.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// TODO : Test 종료 후 삭제해야 한다
public class testController {


//    @GetMapping({ "", "/" })
//    public String index() {
//        return "메인 페이지입니다.";
//    }

    @GetMapping("/login")
    public String login() {
        return "Google 또는 Kakao로 로그인 먼저 하세요";
    }

    @GetMapping("/user")
    public String user(@LoginUser User user) {
        System.out.println("user name" + user.getUsername());
        return "/user로 들어왔습니다. user와 manager만 접근 가능 합니다";
    }

    @GetMapping("/manager")
    public String manager(@LoginUser User user) {
        return "/manager로 들어왔습니다. manager만 접근 가능 합니다";
    }
}
