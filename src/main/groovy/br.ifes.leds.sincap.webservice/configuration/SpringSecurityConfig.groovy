package br.ifes.leds.sincap.webservice.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

import javax.sql.DataSource

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource

    @Autowired
    def configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery(
                'SELECT cpf username, permissao.role FROM funcionario JOIN funcionario_permissao ON funcionario.id = funcionario_permissao.funcionario_id JOIN permissao ON permissao.id = funcionario_permissao.permissoes_id WHERE cpf = ?')
            .usersByUsernameQuery(
                'SELECT cpf username, senha AS password, ativo enabled FROM funcionario WHERE cpf =?')
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers('/**').permitAll()
                .anyRequest().authenticated()
    }
}
