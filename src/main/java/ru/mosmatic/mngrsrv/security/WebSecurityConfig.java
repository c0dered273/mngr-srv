package ru.mosmatic.mngrsrv.security;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/api/anonymous/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServerConfigurer -> resourceServerConfigurer
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();
                    Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);
                    if (jwt.getClaim("realm_access") == null) {
                        return grantedAuthorities;
                    }
                    JSONObject realmAccess = jwt.getClaim("realm_access");
                    if (realmAccess.get("roles") == null) {
                        return grantedAuthorities;
                    }
                    JSONArray roles = (JSONArray) realmAccess.get("roles");
                    final List<SimpleGrantedAuthority> keycloakAuthorities =
                            roles.stream().map(
                                    role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
                    grantedAuthorities.addAll(keycloakAuthorities);
                    return grantedAuthorities;
                }
        );
        return authenticationConverter;
    }
}
