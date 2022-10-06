package com.github.hannahscript.jlibs.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final Set<String> users = new HashSet<>();

    public boolean addUser(String user) {
        if (users.contains(user)) return false;

        users.add(user);
        return true;
    }

    public Set<String> getUsers() {
        return users;
    }

    public boolean hasUser(String username) {
        return this.users.contains(username);
    }

    public int getTotalUsers() {
        return this.users.size();
    }
}
