package br.ifes.leds.sincap.webservice.configuration

import org.dozer.DozerBeanMapper
import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource

@Configuration
@ImportResource(value = 'classpath:spring-shared-context.xml')
@EntityScan(basePackages = ['br.ifes.leds.**.cdp', 'br.ifes.leds.reuse.persistence'])
class SincapEntitiesConfig {

    @Bean
    DozerBeanMapper mapper() {
        def mapper = new DozerBeanMapper()
        mapper.mappingFiles = ['dozerBeanMapping.xml']
        mapper
    }
}
