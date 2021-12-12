package me.imatveev.thechat.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.security.properties.SecurityProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BasicAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager manager;
    private final SecurityProperties properties;
    private final ObjectMapper mapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String login = request.getParameter("phone");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

        return manager.authenticate(token);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        Date expiresAt = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(properties.getExpiredAtMinutes()));

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuer(request.getRequestURL().toString())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .sign(algorithm);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(
                response.getOutputStream(),
                BasicAuthSuccessResponse.builder()
                        .accessToken(accessToken)
                        .username(user.getUsername())
                        .expiredAt(expiresAt)
                        .authorities(user.getAuthorities())
                        .build()
        );
    }

    @Value
    @Builder
    private static class BasicAuthSuccessResponse {
        String accessToken;
        Collection<? extends GrantedAuthority> authorities;
        String username;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date expiredAt;
    }
}
