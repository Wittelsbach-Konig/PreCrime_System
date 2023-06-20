package ru.itmo.PreCrime.config;

// import javax.management.relation.Role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.itmo.PreCrime.model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_WHITELIST = {
        "/css/**",
        "/image/**",
        "/js/**",
        "/includes/**",
        "/registration",
        "/login",
        "/error",
    };
    public static final String [] DETECTIVE_WHITELIST = {
        "/investigation",
        "/cardfill"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_PROCESSING_URL = "/process_login";
    public static final String DEFAULT_SUCCESS_URL = "/cabinet";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers(DETECTIVE_WHITELIST).hasAuthority(Role.DETECTIVE.toString())
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage(LOGIN_URL)
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                        .failureUrl(LOGIN_FAIL_URL)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl(LOGOUT_URL)
                        .logoutSuccessUrl(LOGIN_URL));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
