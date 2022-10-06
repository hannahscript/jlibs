package com.github.hannahscript.jlibs.message.server;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class WelcomeMessage {
    private Set<String> users;

    public WelcomeMessage(Set<String> users) {
        this.users = users;
    }
}
