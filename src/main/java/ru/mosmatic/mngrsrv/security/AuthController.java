package ru.mosmatic.mngrsrv.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mosmatic.mngrsrv.security.dto.UserInfoDto;

/**
 * .
 */
@RestController
@Slf4j
public class AuthController {

    /**
     * For test purposes.
     *
     * @return User info
     */
    @GetMapping("/api/auth/me")
    public UserInfoDto meAns() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new UserInfoDto(
                auth.getName(),
                auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
    }
}
