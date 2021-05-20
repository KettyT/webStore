package ru.tiutikova.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.tiutikova.filter.AuthFilter;
import ru.tiutikova.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyAccessDeniedHandler accessDeniedHandler;

    private UserService userService;

    private AuthFilter authFilter;

    @Autowired
    public SecurityConfiguration (UserService userService) {
        this.authFilter = new AuthFilter(userService);
        this.userService = userService;
    }


    // роль admin всегда есть доступ к /admin/**
    // роль user всегда есть доступ к /user/**
    // Наш кастомный "403 access denied" обработчик
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/**").permitAll();

        http.addFilterBefore(authFilter, BasicAuthenticationFilter.class);
    }

}
