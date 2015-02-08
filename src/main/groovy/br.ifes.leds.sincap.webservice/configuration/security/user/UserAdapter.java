package br.ifes.leds.sincap.webservice.configuration.security.user;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Funcionario;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
public class UserAdapter implements User {

    private String username;
    private String password;
    private List<Autoridade> authorities = new ArrayList<>();
    private boolean enabled;
    private Long expires;

    public UserAdapter() {
    }

    @JsonIgnore
    public UserAdapter(Funcionario funcionario) {
        this.username = funcionario.getCpf();
        this.password = funcionario.getSenha();
        this.enabled = funcionario.isAtivo();

        for (Permissao permissao: funcionario.getPermissoes()) {
            authorities.add(new Autoridade().setPermissao(permissao));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public void setExpires(Long expires) {
        this.expires = expires;
    }

    @Override
    public Long getExpires() {
        return expires;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<Autoridade> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
