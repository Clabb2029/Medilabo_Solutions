package com.medilabo.gateway.configuration;

import com.medilabo.gateway.service.CustomDoctorDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class Security {

    public Security(CustomDoctorDetailsService customDoctorDetailsService) {
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/microservice-frontend/styles.css", "/microservice-frontend/login").permitAll()
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/microservice-frontend/login"))
                )
                .formLogin(form -> form
                        .loginPage("/microservice-frontend/login")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            webFilterExchange.getExchange().getSession()
                                    .flatMap(webSession -> {
                                        webSession.getAttributes().remove("SPRING_SECURITY_SAVED_REQUEST");
                                        return Mono.empty();
                                    })
                                    .subscribe();
                            log.info("Connexion réussie pour : {}", authentication.getName());
                            return new RedirectServerAuthenticationSuccessHandler("/microservice-frontend/patient-list")
                                    .onAuthenticationSuccess(webFilterExchange, authentication);
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> webFilterExchange.getExchange().getFormData()
                                .doOnNext(formData -> {
                                    log.warn("Erreur d'authentification : {}", exception.getMessage());
                                })
                                .then(new RedirectServerAuthenticationFailureHandler("/microservice-frontend/login?error")
                                        .onAuthenticationFailure(webFilterExchange, exception)))
                )
                .logout(logout -> {
                    RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
                    logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/microservice-frontend/login"));
                    logout.logoutUrl("/logout")
                            .logoutSuccessHandler(logoutSuccessHandler);
                });
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
