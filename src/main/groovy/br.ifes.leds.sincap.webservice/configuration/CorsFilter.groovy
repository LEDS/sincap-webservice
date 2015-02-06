package br.ifes.leds.sincap.webservice.configuration

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE

/**
 * Filtro que adiciona Headers que permitem qualquer cliente acessar nosso
 * servidor.
 */
@Component
@Order(HIGHEST_PRECEDENCE) //Executa o filtro antes do Spring Security
class CorsFilter implements Filter {

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        def request = servletRequest as HttpServletRequest
        def response = servletResponse as HttpServletResponse

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        if (request.method != 'OPTIONS')
            filterChain.doFilter(servletRequest, servletResponse)
    }

    @Override
    void destroy() {

    }
}
