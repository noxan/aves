package com.github.noxan.aves.demo;

import java.io.IOException;

import com.github.noxan.aves.auth.AuthException;
import com.github.noxan.aves.auth.accessor.UsernamePassword;
import com.github.noxan.aves.auth.accessor.UsernamePasswordAccessor;
import com.github.noxan.aves.auth.storage.InMemoryUsernamePasswordStorage;
import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.server.ServerHandler;
import com.github.noxan.aves.server.SocketServer;

public class AuthServer implements ServerHandler {
    public static void main(String[] args) {
        new AuthServer();
    }

    private InMemoryUsernamePasswordStorage userStorage;

    public AuthServer() {
        userStorage = new InMemoryUsernamePasswordStorage();
        userStorage.addUser("noxan", "123");

        SocketServer server = new SocketServer(this);
        server.start();
    }

    @Override
    public void clientConnect(Connection connection) {
    }

    @Override
    public void clientDisconnect(Connection connection) {
    }

    @Override
    public void clientLost(Connection connection) {
    }

    @Override
    public void readData(Connection connection, Object data) {
        String parts[] = ((String) data).trim().split(" ");
        String message = parts[0].toUpperCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        try {
            switch (message) {
            case "LOGIN":
                if (args.length > 1) {
                    UsernamePasswordAccessor accessor = new UsernamePassword(args[0], args[1]);
                    try {
                        String user = userStorage.requestUser(accessor);
                        connection.write("WELCOME " + user);
                    } catch (AuthException e) {
                        connection.write("LOGIN ERROR: " + e.getMessage());
                    }
                } else {
                    connection.write("LOGIN ERROR: Invalid parameter(s)");
                }
                break;
            case "LOGOUT":
                connection.write("Not implemented yet!");
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
