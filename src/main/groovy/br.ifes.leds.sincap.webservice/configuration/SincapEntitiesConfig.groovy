package br.ifes.leds.sincap.webservice.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource

import javax.persistence.EntityManagerFactory

@Configuration
@ImportResource(value = 'classpath:spring-context.xml')
class SincapEntitiesConfig {
    @Autowired
    EntityManagerFactory myEmf

    /**
     * Método que adapta a configuração de banco de dados do sincap entities.
     * O Spring Boot espera uma bean com o nome entityManagerFactory, porém o
     * sincap-entities cria uma bean com o nome de myEmf.
     *
     * @return Uma bean EntityManagerFactory
     */
    @Bean
    def entityManagerFactory() {
        myEmf
    }
}
