package br.ifes.leds.sincap.webservice

import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno
import br.ifes.leds.sincap.webservice.configuration.security.UserAuthentication
import br.ifes.leds.sincap.webservice.util.Autoridade
import br.ifes.leds.sincap.webservice.util.UserAdapter
import br.ifes.leds.sincap.webservice.util.UserDetailsService
import br.ifes.leds.sincap.webservice.util.UserDetailsServiceAdapter
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MvcResult

import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.TipoNaoDoacao.PROBLEMAS_ESTRUTURAIS
import static br.ifes.leds.sincap.webservice.AuthenticationTests.UserBuilder.userBuilder
import static br.ifes.leds.sincap.webservice.service.ContextUrls.APP_NOTIFICACAO_CAPTACAO
import static br.ifes.leds.sincap.webservice.service.ContextUrls.GET_ASPECTOSLOGISTICOS
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationTests extends BaseMocksTests {

    @Configuration
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

    static class UserBuilder {
        UserAdapter user

        static userBuilder() {
            new UserBuilder()
        }

        UserBuilder() {
            user = new UserAdapter()
            this
        }

        UserBuilder authorities(String... authorities) {
            List<Autoridade> autoridades = []
            authorities.each { authority -> autoridades.add(new Autoridade().setAuthority(authority)) }
            this
        }

        UserBuilder username(String username) {
            this.user.username = username
            this
        }

        UserBuilder password(String password) {
            this.user.password = password
            this
        }

        UserBuilder enabled(boolean enabled) {
            this.user.enabled = enabled
            this
        }

        UserBuilder expires(Long expires) {
            this.user.expires = expires
            this
        }

        UserAdapter build() {
            this.user
        }
    }
}
