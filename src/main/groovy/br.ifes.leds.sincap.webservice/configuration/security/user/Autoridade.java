package br.ifes.leds.sincap.webservice.configuration.security.user;


import br.ifes.leds.sincap.controleInterno.cln.cdp.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode
public class Autoridade implements GrantedAuthority {
    private String authority;

    @JsonIgnore
    public Autoridade setPermissao(Permissao permissao) {
        authority = permissao.getRole();
        return this;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Autoridade setAuthority(String authority, String ignored) {
        this.authority = authority;
        return this;
    }
}
