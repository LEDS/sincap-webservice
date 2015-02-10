package br.ifes.leds.sincap.webservice

import org.junit.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

import static org.springframework.http.MediaType.APPLICATION_JSON

@ActiveProfiles('test')
@IntegrationTest('server.port:0')
class CaptacaoServiceTest extends BaseMocksTests {

    @Value('${local.server.port}')
    int port

    @Test
    void getListaNotificacoes(){
        doAuthenticatedExchange('222.222.222-22', HttpMethod.GET, 'captacao', '{"bancoolhos": {"id": "2"}}', 'abc123')
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
}
