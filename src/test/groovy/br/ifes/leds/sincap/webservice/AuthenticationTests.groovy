package br.ifes.leds.sincap.webservice

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno
import br.ifes.leds.sincap.webservice.configuration.security.user.UserAdapter
import br.ifes.leds.sincap.webservice.configuration.security.user.UserDetailsService
import br.ifes.leds.sincap.webservice.configuration.security.user.UserDetailsServiceAdapter
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MvcResult

import static br.ifes.leds.sincap.webservice.UserBuilder.userBuilder
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles(['test', 'AuthenticationTests'])
class AuthenticationTests extends BaseMocksTests {

    @Configuration
    @Profile('AuthenticationTests')
    static class UserDetailServiceConfig {
        @Bean
        @Primary
        UserDetailsService userDetailsService() {
            mock(UserDetailsServiceAdapter.class)
        }

        @Bean
        @Primary
        AplCadastroInterno aplCadastroInterno() {
            mock(AplCadastroInterno.class)
        }
    }

    @Autowired
    UserDetailsService userDetailsService
    @Autowired
    AplCadastroInterno aplCadastroInterno

    @Test
    void loginTest() {
        when(userDetailsService.loadUserByUsername('432.763.987-54')).thenReturn(captador())

        MvcResult mvcResult = mockMvc.perform(post('/api/login')
                .contentType(APPLICATION_JSON)
                .content('{"username": "432.763.987-54", "password": "abc123"}'))
                .andExpect(status().isOk()).andReturn()

        assertNotNull('X-AUTH-TOKEN é nulo!', mvcResult.response.getHeader('X-AUTH-TOKEN'))
    }

    private static UserAdapter captador() {
        userBuilder().username('432.763.987-54').password('abc123').enabled(true)
                .authorities('ROLE_CAPTADOR')
                .build()
    }

}
