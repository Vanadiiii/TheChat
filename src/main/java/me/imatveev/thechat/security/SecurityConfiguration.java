package me.imatveev.thechat.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.imatveev.thechat.security.filter.BasicAuthFilter;
import me.imatveev.thechat.security.properties.SecurityProperties;
import me.imatveev.thechat.security.service.UserSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder encoder;
    private final SecurityProperties properties;
    private final UserSecurityService userService;
    private final ObjectMapper mapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BasicAuthFilter basicAuthFilter = new BasicAuthFilter(
                authenticationManager(), properties, mapper
        );

        http.csrf().disable();
        http.cors().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(properties.getAllowedEndpoints().toArray(String[]::new)).permitAll()
                .antMatchers("/**").authenticated();

        http.formLogin(
                form -> form.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/swagger-ui.html")
                        .successForwardUrl("/swagger-ui.html")
                        .permitAll()
        );

        http.addFilter(basicAuthFilter);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
