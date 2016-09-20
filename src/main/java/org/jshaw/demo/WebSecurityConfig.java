package org.jshaw.demo;

import org.jshaw.demo.security.TokenAuthenticationFilter;
import org.jshaw.demo.security.TokenAuthenticationProcessingFilter;
import org.jshaw.demo.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public WebSecurityConfig(TokenAuthenticationService tokenAuthenticationService,
                             UserDetailsService userDetailsService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().cacheControl().disable().and()
                .exceptionHandling().and()
                .servletApi().and()
                .anonymous().and()
                .authorizeRequests()

                //allow all static resources
                .antMatchers("/favicon.ico", "/js/**", "/template/**", "/bower_components/**").permitAll()

                //allow anonymous GETs to index, login, signup page
                .antMatchers(HttpMethod.GET, "/", "index.html", "/api/user/current").permitAll()

                //allow anonymous POSTs to login, signup
                .antMatchers(HttpMethod.POST, "/api/login", "/api/signup").permitAll()

                //authenticate all others
                .anyRequest().authenticated().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new TokenAuthenticationProcessingFilter("/api/login", "POST", tokenAuthenticationService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new TokenAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }
}
