package com.training.tinelli.sales.config;

import com.training.tinelli.sales.JwtService;
import com.training.tinelli.sales.security.jwt.JwtAuthFilter;
import com.training.tinelli.sales.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtService jwtService;

    /* Serve para criptografar e descriptografar a senha do usuário */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userService);
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
                .antMatchers(HttpMethod.POST,"/api/v0/users/**").permitAll() // para essa rota, o verbo POST é permitido por qualquer um
                .anyRequest().authenticated() // qualquer outra rota tem que estar autenticado
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
