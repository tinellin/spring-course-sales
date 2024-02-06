package com.training.tinelli.sales.security.jwt;

import com.training.tinelli.sales.service.impl.JwtService;
import com.training.tinelli.sales.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Atua como filtro (middleware) interceptando todas as requisições protegidas, verificando se o token é válido
    e inserindo as credenciais de autenticação para informar ao contexto do Spring Security e as nossas rotas
    que o usuário está autenticado e possui acesso
*/
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public final JwtService jwtService;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletReq, HttpServletResponse httpServletRes, FilterChain filterChain) throws ServletException, IOException {
        /* Obter o token a partir do header Authorization */
        String authorization = httpServletReq.getHeader("Authorization");

        /* Verifica se o header não é nulo e começa com "Bearer" */
        if (authorization != null && authorization.startsWith("Bearer")) {
            /* Elimino o prefixo "Bearer " e iremos obter o token */
            String token = authorization.substring("Bearer ".length());

            /* Verifica se o token é válido */
            if (jwtService.isTokenValid(token)) {
                /*
                    Pelo método interno getClaims(): realiza o parser, acessa o corpo do token e coleta o subject,
                    que carrega a info. de username
                */
                String username = jwtService.getUsername(token);

                /*
                    Do username, obtemos o obj. userDetails que contêm todas as informações necessárias (username, password, roles, authorities)
                    para gerar a credencial
                 */
                UserDetails userDetails = userService.loadUserByUsername(username);

                /* A credencial vai dizer a nossa aplicação que o usuário está autenticado e possui acesso */
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                /* Devemos dizer ao contexto do Spring Security que a credencial é para um app web */
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletReq));

                /* Por fim, acessamos o contexto do Spring Security para obter ou definir o usuário autenticado */
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        /* Remover a interceptação, usando esse método que continua a requisição (mas com as credenciais de autenticação)*/
        filterChain.doFilter(httpServletReq, httpServletRes);
    }
}
