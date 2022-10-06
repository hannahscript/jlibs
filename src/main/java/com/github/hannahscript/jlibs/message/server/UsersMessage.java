package com.github.hannahscript.jlibs.message.server;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UsersMessage {
    private List<String> users;

    public UsersMessage(List<String> users) {
        this.users = users;
    }
}
