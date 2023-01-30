package com.solmaz.ticketplannermainservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/register/**").permitAll()
                        .antMatchers("/", "/home").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/api/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/api/voyages/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.GET, "/api/voyages").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/api/voyages/**").hasAnyAuthority("ADMIN")

                        .antMatchers(HttpMethod.GET, "/api/tickets/byTelNo/**").hasAnyAuthority("ADMIN", "USER")
                        .antMatchers(HttpMethod.GET, "/api/tickets/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST, "/api/tickets/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/api/tickets/**").hasAnyAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/api/tickets/**").hasAnyAuthority("ADMIN")

                        .antMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
    }
}
