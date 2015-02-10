package br.ifes.leds.sincap.webservice

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ObitoDTO
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.PacienteDTO
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.dto.ProcessoNotificacaoDTO
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplProcessoNotificacao
import br.ifes.leds.sincap.webservice.configuration.security.user.UserDetailsService
import br.ifes.leds.sincap.webservice.configuration.security.user.UserDetailsServiceAdapter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import lombok.Getter
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.mockito.stubbing.OngoingStubbing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

import java.util.regex.Matcher

import static br.ifes.leds.sincap.controleInterno.cln.cdp.Sexo.MASCULINO
import static br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoNotificacaoEnum.AGUARDANDOCAPTACAO
import static br.ifes.leds.sincap.webservice.UserBuilder.userBuilder
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is

import static org.junit.Assert.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles(['test', 'CaptacaoServiceTest'])
@IntegrationTest('server.port:0')
class CaptacaoServiceTest extends BaseMocksTests {

    @Value('${local.server.port}')
    int port
    @Autowired
    UserDetailsService userDetailsService
    @Autowired
    AplProcessoNotificacao aplProcessoNotificacao
    @Getter(lazy = true)
    ObjectMapper objectMapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Before
    void before() {
        when(userDetailsService.loadUserByUsername('222.222.222-22')).thenReturn(userBuilder().username('222.222.222-22').password('abc123').authorities('ROLE_CAPTADOR').enabled(true).build())
        when(userDetailsService.loadUserByUsername('111.111.111-11')).thenReturn(userBuilder().username('111.111.111-11').password('abc123').authorities('ROLE_NOTIFICADOR').enabled(true).build())
    }

    @Test
    void getListaNotificacoes(){
        def processosFromBd = processoNotificacaoComPaciente()

        configureAplProcessoNotifMock(processosFromBd)
        def resultado = doAuthenticatedExchange('222.222.222-22', HttpMethod.GET, 'captacao?bancoolhos.id=2', null, 'abc123')

        def processosFromController = fromJSON(resultado)

        assertThat('Quantidade de processos retornados tem tamanho errado',
                processosFromController.size(), is(1))
        assertThat('Dados retornados pelo JSON não batem com os dados que saíram do banco de dados',
                processosFromController.get(0).obito.paciente, equalTo(processosFromBd.get(0).obito.paciente))
    }

    private String doAuthenticatedExchange(final String user, final HttpMethod method, final String path) {
        return doAuthenticatedExchange(user, method, path, null, user);
    }


    private String doAuthenticatedExchange(final String user, final HttpMethod method, final String path,
                                           String request, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        HttpEntity<String> login = new HttpEntity<>(
                "{\"username\":\"" + user + "\",\"password\":\"" + password + "\"}", httpHeaders);
        ResponseEntity<Void> results = restTemplate.postForEntity("http://localhost:" + port + "/api/login", login, Void.class);

        httpHeaders.add("X-AUTH-TOKEN", results.getHeaders().getFirst("X-AUTH-TOKEN"));
        HttpEntity<String> testRequest = new HttpEntity<>(request, httpHeaders);
        HttpEntity<String> testResponse = restTemplate.exchange("http://localhost:" + port + "/" + path, method, testRequest,
                String.class);
        return testResponse.getBody();
    }

    private def configureAplProcessoNotifMock(ArrayList<ProcessoNotificacaoDTO> processosFromBd) {
        when(aplProcessoNotificacao.retornarNotificacaoPorEstadoAtualEBancoOlhos(AGUARDANDOCAPTACAO, 2L)).thenReturn(processosFromBd)
    }

    private static ArrayList<ProcessoNotificacaoDTO> processoNotificacaoComPaciente() {
        [ProcessoNotificacaoDTO.builder().id(1L).obito(ObitoDTO.builder().paciente(PacienteDTO.builder().nome('Paciente Qualquer').sexo(MASCULINO).nomeMae('Nome da Mãe').build()).build()).build()]
    }

    private List<ProcessoNotificacaoDTO> fromJSON(String resultado) {
        getObjectMapper().readValue(resultado, objectMapper.getTypeFactory().constructCollectionType(List, ProcessoNotificacaoDTO))
    }

    @Configuration
    @Profile('CaptacaoServiceTest')
    static class UserDetailServiceConfig {
        @Bean
        @Primary
        UserDetailsService userDetailsService() {
            mock(UserDetailsServiceAdapter.class)
        }

        @Bean
        @Primary
        AplProcessoNotificacao aplProcessoNotificacao() {
            mock(AplProcessoNotificacao.class)
        }
    }
}
