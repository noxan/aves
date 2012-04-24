package com.github.noxan.aves.auth.storage;

import java.util.HashMap;
import java.util.Map;

import com.github.noxan.aves.auth.accessor.UsernamePasswordAccessor;

public class InMemoryUsernamePasswordStorage {
    private Map<String, String> users;

    public InMemoryUsernamePasswordStorage() {
        users = new HashMap<String, String>();
    }

    public void addUser(String username, String password) {
        users.put(username, password);
    }

    public String requestUser(UsernamePasswordAccessor accessor) {
        if (accessor.checkPassword(users.get(accessor.getUsername()))) {
            return accessor.getUsername();
        }
        return null;
    }
}
