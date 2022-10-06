package com.github.hannahscript.jlibs.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthenticator {
    public UsernamePasswordAuthenticationToken authenticate(String username, String password) {
        // todo autenticate with generated password
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
