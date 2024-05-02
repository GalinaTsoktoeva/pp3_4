package com.example.demo.config;

import com.example.demo.config.handler.LoginSuccessHandler;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalAuthentication
public class SecurityConfig {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {
        http
//                .cors(httpSecurityCorsConfigurer -> )
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user-list").authenticated()
                        .requestMatchers("/user-info").authenticated()
                                .requestMatchers("/admin/users").hasRole("ADMIN")
//                                                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/users").permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                        )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


             return http.build();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
