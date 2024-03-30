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
                                        HttpMethod.GET,
                                        "/employee/{employeeId}"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name(), Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/employee/{employeeId}"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name(), Scope.EMPLOYEE.name())
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
                                ).hasAnyAuthority(Scope.EMPLOYEE.name(), Scope.ADMIN.name(), Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/holiday/type"
                                ).hasAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/holiday/type/{holidayTypeId}"
                                ).hasAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/holiday/type",
                                        "/holiday/my-holidays",
                                        "/holiday/type/{holidayTypeId}"
                                ).hasAnyAuthority(Scope.EMPLOYEE.name(), Scope.ADMIN.name(), Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/holiday/type/{holidayTypeId}"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/holiday/{holidayId}"
                                ).hasAnyAuthority(Scope.EMPLOYEE.name(), Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/holiday/{holidayId}",
                                        "/validate/{holidayId}",
                                        "/publish/{holidayId}",
                                        "/unapproved/{holidayId}",
                                        "/unpublished/{holidayId}"
                                ).hasAnyAuthority(Scope.ADMIN.name(), Scope.SUPER_ADMIN.name(), Scope.EMPLOYEE.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/holiday/{holidayId}"
                                ).hasAnyAuthority(Scope.EMPLOYEE.name(), Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/config/holiday"
                                ).hasAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/config/holiday/{holidayConfigId}/activate",
                                        "/config/holiday/{holidayConfigId}/deactivate"
                                ).hasAuthority(Scope.SUPER_ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/config/holiday",
                                        "/config/{holidayConfigId}",
                                        "/holiday-type/{holidayTypeId}/config"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name())
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/holiday/{holidayId}/notice",
                                        "/holiday-type/{holidayTypeId}/config/active"
                                ).hasAnyAuthority(Scope.SUPER_ADMIN.name(), Scope.ADMIN.name(), Scope.EMPLOYEE.name())
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
                .requestMatchers("/v2/**")
                .requestMatchers("/auth/authenticate")
                .requestMatchers("/hello")
                .requestMatchers(
                        HttpMethod.POST,
                        "/admin/registration"
                );
    }
}
