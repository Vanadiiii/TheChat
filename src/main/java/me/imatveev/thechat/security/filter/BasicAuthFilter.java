package me.imatveev.thechat.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.security.properties.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
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
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

        return manager.authenticate(token);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(properties.getExpiredAtMinutes())))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(
                        "roles",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .sign(algorithm);

        Map<String, String> authResponse = Map.of("access_token", accessToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), authResponse);
    }
}
