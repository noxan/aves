package com.github.noxan.aves.auth.storage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUsernamePasswordStorage {
    private Map<String, String> users;

    public InMemoryUsernamePasswordStorage() {
        users = new HashMap<String, String>();
    }

    public void addUser(String username, String password) {
        users.put(username, password);
    }
}
