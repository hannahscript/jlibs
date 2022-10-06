package com.github.hannahscript.jlibs.message.server;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserConnectedMessage {
    private String username;

    public NewUserConnectedMessage(String username) {
        this.username = username;
    }
}
