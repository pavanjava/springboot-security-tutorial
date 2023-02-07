package com.sparkdev.securityworkshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${application.user1.name}")
    private String adminUserName;
    @Value("${application.user1.password}")
    private String adminUserPassword;
    @Value("${application.user1.role}")
    private String adminRole;

    @Value("${application.user2.name}")
    private String normalUserName;
    @Value("${application.user2.password}")
    private String normalUserPassword;
    @Value("${application.user2.role}")
    private String userRole;
    @Bean
    // open the /greet api without security.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/greet").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**")
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .build();

    }

    // below method serve the purpose of Authentication and Authorization
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails adminUser = User.withUsername(adminUserName)
                .password(passwordEncoder.encode(adminUserPassword))
                .roles(adminRole)
                .build();

        UserDetails normalUser = User.withUsername(normalUserName)
                .password(passwordEncoder.encode(normalUserPassword))
                .roles(userRole)
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
