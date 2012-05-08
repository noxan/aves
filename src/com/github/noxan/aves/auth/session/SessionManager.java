package com.github.noxan.aves.auth.session;

import java.util.HashMap;
import java.util.Map;

import com.github.noxan.aves.auth.AuthException;
import com.github.noxan.aves.auth.User;
import com.github.noxan.aves.auth.accessor.UsernamePasswordAccessor;
import com.github.noxan.aves.auth.storage.InMemoryUsernamePasswordStorage;
import com.github.noxan.aves.net.Connection;

public class SessionManager {
    private InMemoryUsernamePasswordStorage storage;
    private Map<Connection, User> map;

    public SessionManager(InMemoryUsernamePasswordStorage storage) {
        this.storage = storage;
        map = new HashMap<Connection, User>();
    }

    public User getSession(Connection connection) throws AuthException {
        if(map.containsKey(connection)) {
            return map.get(connection);
        }
        throw new AuthException("Session does not exist for this connection");
    }

    public User requestSession(UsernamePasswordAccessor accessor, Connection connection) throws AuthException {
        User user = storage.requestUser(accessor);
        if(map.containsKey(connection)) {
            System.out.println("Connection exists");
            return user;
        } else if(map.containsValue(user)) {
            throw new AuthException("User already logged in.");
        } else {
            System.out.println("Session created");
            map.put(connection, user);
            return user;
        }
    }

    public void destroySession(Connection connection) {
        System.out.println("destroy session: " + map.remove(connection));
    }
}
