package br.ifes.leds.sincap.webservice.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

import javax.sql.DataSource


@Configuration
@Profile('production')
class HibernateConfig {

    @Bean
    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                    @Value('${hibernate.dialect}') String dialect,
                                                    @Value('${hibernate.cache.provider_class}') String provider) {
        def factory = new LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.jpaVendorAdapter = new HibernateJpaVendorAdapter()
        factory.setJpaPropertyMap([
                'hibernate.dialect': dialect,
                'hibernate.cache.provider_class': provider
        ])

        factory
    }
}
