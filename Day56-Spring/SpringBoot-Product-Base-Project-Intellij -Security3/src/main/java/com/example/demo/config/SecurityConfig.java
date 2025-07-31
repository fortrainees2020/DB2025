package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
/* @Configuration is an annotation used to define a class as a source of bean definitions
*  for the Spring ApplicationContext.*/
@EnableWebSecurity
@EnableMethodSecurity
/*
* @EnableMethodSecurity is a Spring Security annotation used to enable method-level
* security annotations such as @PreAuthorize, @PostAuthorize, @Secured, and @RolesAllowed.
* */
public class SecurityConfig {

    @Bean
    /*A Spring Bean is an object that is instantiated, assembled, and managed by a Spring IoC
    (Inversion of Control) container.*/
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable()
                .authorizeHttpRequests((authorize)->{
                    authorize.requestMatchers("/hello").permitAll()
                     .requestMatchers("/api/products/**").hasAnyRole("USER", "ADMIN")
                            //.authorize.anyRequest().authenticated();
                            .anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails john = User.builder()
                .username("john")
                .password(passwordEncoder().encode("john"))
                .roles("USER")
                .build();

        UserDetails sam = User.builder()
                .username("sam")
                .password(passwordEncoder().encode("sam"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john,sam);
    }
}