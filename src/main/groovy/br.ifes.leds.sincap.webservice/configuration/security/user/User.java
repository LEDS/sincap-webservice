package br.ifes.leds.sincap.webservice.configuration.security.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface User extends UserDetails {

    public void setExpires(Long expires);

    public Long getExpires();
}