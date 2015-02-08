package br.ifes.leds.sincap.webservice.configuration.security

import br.ifes.leds.sincap.webservice.util.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.bind.DatatypeConverter

import static java.lang.System.currentTimeMillis

@Service
class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

    private final TokenHandler tokenHandler;

    // TODO: Substituir a chave por um campo em application.properties
    @Autowired
    def TokenAuthenticationService(@Value('9SyECk96oDsTmXfogIieDI0cD/8FpnojlYSUJT5U9I/FGVmBz5oskmjOR8cbXTvoPjX+Pq/T/b1PqpHX0lYm0oCBjXWICA==') String secret) {
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret))
    }

    def addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.details as User
        user.expires = currentTimeMillis() + TEN_DAYS
        response.addHeader AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user)
    }

    def getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader AUTH_HEADER_NAME

        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(token)
            if (user != null) {
                new UserAuthentication(user)
            }
        }
    }
}
