package br.ifes.leds.sincap.webservice.util

import br.ifes.leds.sincap.webservice.configuration.security.user.User
import org.springframework.security.core.context.SecurityContextHolder

class CurrentUserUtil {

    static def User currentUser() {
        SecurityContextHolder.context.authentication.details as User
    }
}
