package br.ifes.leds.sincap.webservice.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource

@Configuration
@ImportResource(value = 'classpath:spring-context.xml')
class SincapEntitiesConfig {
}
