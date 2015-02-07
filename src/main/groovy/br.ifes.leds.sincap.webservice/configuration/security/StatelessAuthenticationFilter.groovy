package br.ifes.leds.sincap.webservice.configuration.security

import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

import static org.springframework.security.core.context.SecurityContextHolder.context

class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService tokenAuthenticationService

    StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService
    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        context.setAuthentication(tokenAuthenticationService.getAuthentication(servletRequest as HttpServletRequest))
        filterChain.doFilter(servletRequest, servletResponse)
    }
}
