package br.ifes.leds.sincap.webservice.util;


import br.ifes.leds.sincap.controleInterno.cln.cdp.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

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
