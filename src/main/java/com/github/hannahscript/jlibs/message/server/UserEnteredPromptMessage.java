package com.github.hannahscript.jlibs.message.server;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEnteredPromptMessage {
    private String username;

    public UserEnteredPromptMessage(String username) {
        this.username = username;
    }
}
