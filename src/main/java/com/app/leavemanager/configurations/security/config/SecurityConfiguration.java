package com.app.leavemanager.configurations.security.config;

import com.app.leavemanager.configurations.security.config.filter.JwtAuthenticationFilter;
import com.app.leavemanager.configurations.security.service.LogoutService;
import com.app.leavemanager.domain.employee.user.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/admin/add"
                                ).hasAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/admin/employee/add"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/admin/employee/all",
                                        "/holiday/all",
                                        "/holiday/{holidayId}"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name(), Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/holiday"
                                ).hasAuthority(Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/holiday/type"
                                ).hasAuthority(Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/holiday/type",
                                        "/holiday/type/{holidayTypeId}"
                                ).hasAnyAuthority(Scope.EMPLOYEE.name(), Scope.ADMIN.name(), Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/holiday/{holidayId}"
                                ).hasAuthority(Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/holiday/{holidayId}",
                                        "/approve/{holidayId}",
                                        "/publish/{holidayId}",
                                        "/unapproved/{holidayId}",
                                        "/unpublished/{holidayId}"
                                ).hasAuthority(Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/holiday/{holidayId}"
                                ).hasAuthority(Scope.EMPLOYEE.name())
                                .anyRequest().denyAll()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoreResources() {
        return (webSecurity) -> webSecurity
                .ignoring()
                .requestMatchers("/h2-console/**")
                .requestMatchers("/actuator/health/**")
                .requestMatchers("/actuator/swagger-ui")
                .requestMatchers("/api/auth/authenticate")
                .requestMatchers(
                        HttpMethod.POST,
                        "/admin/registration"
                );
    }
}
