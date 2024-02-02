package com.training.tinelli.sales.config;

import com.training.tinelli.sales.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    /* Serve para criptografar e descriptografar a senha do usuário */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Autenticação */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    /* Definir as rotas HTTPs que serão protegidas e quais roles poderão acessar, além de outras configurações*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // como é API Rest(ful) desabilitamos o csrf, pois trabalhamos com stateless
            .authorizeRequests()
                .antMatchers("/api/v0/clients/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v0/orders/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v0/products/**").hasRole("ADMIN")
                .and()
                    .httpBasic(); // permite a passagem por header, e.g. "Authorization"
    }

}