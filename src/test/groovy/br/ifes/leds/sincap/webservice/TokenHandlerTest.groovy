package br.ifes.leds.sincap.webservice

import br.ifes.leds.sincap.webservice.configuration.security.TokenHandler
import br.ifes.leds.sincap.webservice.configuration.security.user.User
import org.junit.Before
import org.junit.Test

import static br.ifes.leds.sincap.webservice.UserBuilder.userBuilder
import static javax.xml.bind.DatatypeConverter.parseBase64Binary
import static org.junit.Assert.assertEquals

class TokenHandlerTest {
    private final static TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
    TokenHandler tokenHandler

    @Before
    void before() {
        tokenHandler = new TokenHandler(parseBase64Binary('theVeryUltraTopSecretKey'))
    }

    @Test
    void tokenCreationAndParseTest() {
        final User originalUser = createUser()
        final String token = tokenHandler.createTokenForUser(originalUser)
        final User recoveredUser = tokenHandler.parseUserFromToken(token)

        assertEquals('Usuário recuperado do token é diferente do original.',
            originalUser, recoveredUser)
    }

    static User createUser() {
        userBuilder().username('username').authorities('ROLE_CAPTADOR')
            .expires(daquiADezDias()).build()
    }

    static Long daquiADezDias() {
        new Date().time + TEN_DAYS
    }

}
