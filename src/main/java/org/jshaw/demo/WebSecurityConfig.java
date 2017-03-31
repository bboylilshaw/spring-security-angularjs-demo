package org.jshaw.demo;

import org.jshaw.demo.security.TokenAuthenticationFilter;
import org.jshaw.demo.security.TokenAuthenticationProcessingFilter;
import org.jshaw.demo.security.TokenAuthenticationService;
import org.jshaw.demo.security.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String secret;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Value("${token.secret}") String secret,
                             UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenHandler tokenHandler() {
        return new TokenHandler(secret, userDetailsService);
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService(tokenHandler());
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenAuthenticationService());
    }

    @Bean
    public TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter() throws Exception {
        return new TokenAuthenticationProcessingFilter("/api/login",
                tokenAuthenticationService(), authenticationManager());
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
                .authorizeRequests()

                //allow all static resources
                .antMatchers("/js/**", "/lib/**", "/template/**").permitAll()

                //allow anonymous GETs to index page
                .antMatchers(HttpMethod.GET, "/", "/index.html").permitAll()

                //allow anonymous POSTs to login, signup
                .antMatchers(HttpMethod.POST, "/api/login", "/api/signup").permitAll()

                //authenticate all others
                .anyRequest().authenticated().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(tokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)

                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
