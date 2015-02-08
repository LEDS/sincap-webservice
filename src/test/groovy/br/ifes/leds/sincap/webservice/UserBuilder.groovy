package br.ifes.leds.sincap.webservice

import br.ifes.leds.sincap.webservice.configuration.security.user.Autoridade
import br.ifes.leds.sincap.webservice.configuration.security.user.UserAdapter

class UserBuilder {
    UserAdapter user

    static userBuilder() {
        new UserBuilder()
    }

    UserBuilder() {
        user = new UserAdapter()
        this
    }

    UserBuilder authorities(String... authorities) {
        this.user.authorities = []
        authorities.each { auth -> this.user.authorities.add(new Autoridade().setAuthority(auth, null))}
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