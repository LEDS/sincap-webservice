package br.ifes.leds.sincap.webservice.configuration.security

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario
import br.ifes.leds.sincap.webservice.model.User
import br.ifes.leds.sincap.webservice.util.User
import br.ifes.leds.sincap.webservice.util.UserAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

/**
 * Created by phillipe on 06/02/15.
 */
class UserAuthentication implements Authentication {

    private final User user
    private boolean authenticated = true

    public UserAuthentication(User user) {
        this.user = user
    }

    public UserAuthentication(Funcionario funcionario) {
        this.user = new UserAdapter(funcionario)
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        user.authorities
    }

    @Override
    Object getCredentials() {
        user.password
    }

    @Override
    Object getDetails() {
        user
    }

    @Override
    Object getPrincipal() {
        user.username
    }

    @Override
    boolean isAuthenticated() {
        authenticated
    }

    @Override
    void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated
    }

    @Override
    String getName() {
        user.username
    }
}
