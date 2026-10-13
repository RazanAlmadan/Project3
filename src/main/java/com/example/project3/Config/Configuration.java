package com.example.project3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/account/add", "/api/v1/account/get/{accountId}", "/api/v1/account/my/accounts", "/api/v1/account/update/{accountId}", "/api/v1/account/delete/{accountId}"
                                , "/api/v1/account/activate/{accountId}", "/api/v1/account/deposit/{accountId}", "/api/v1/account/withdraw/{accountId}", "/api/v1/account/transfer/{fromAccountId}/{toAccountId}",
                                "/api/v1/customer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/employee/**").hasAuthority("EMPLOYEE")
                        .requestMatchers("/api/v1/account/get", "/api/v1/auth/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)

                )
                .httpBasic(basic ->{})
                .build();

    }

}
