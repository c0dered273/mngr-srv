package ru.mosmatic.mngrsrv.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mosmatic.mngrsrv.auth.dto.TestDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/test")
    public TestDto testAns() {
        return new TestDto("test_ok");
    }

}
