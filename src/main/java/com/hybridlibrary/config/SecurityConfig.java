package com.hybridlibrary.config;

import com.hybridlibrary.utils.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/user", "/user/**")
                .hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.GET, ApplicationConstants.BOOK_URL, ApplicationConstants.BOOK_AND_MORE_URL)
                .hasAnyRole(ApplicationConstants.ROLE_ADMIN, ApplicationConstants.ROLE_USER)

                .antMatchers(HttpMethod.GET, "/**/mostrented", "/bookcopy/overdue").hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.GET, "/bookcopy").hasAnyRole(ApplicationConstants.ROLE_USER, ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.DELETE, ApplicationConstants.BOOK_AND_MORE_URL).hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.POST, ApplicationConstants.BOOK_URL, ApplicationConstants.BOOK_AND_MORE_URL).hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.PUT, ApplicationConstants.BOOK_URL, ApplicationConstants.BOOK_AND_MORE_URL).hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers(HttpMethod.DELETE, "/bookcopy/**").hasRole(ApplicationConstants.ROLE_ADMIN)

                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/h2", "/h2/**").permitAll()

                .and()
                .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
