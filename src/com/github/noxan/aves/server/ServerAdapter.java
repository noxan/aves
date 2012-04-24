package com.github.noxan.aves.server;

import com.github.noxan.aves.net.Connection;

public class ServerAdapter implements ServerHandler {
    @Override
    public void clientConnect(Connection connection) {
    }

    @Override
    public void clientDisconnect(Connection connection) {
    }

    public void clientLost(Connection connection) {
    };

    public void readData(Connection connection, Object data) {
    };
}
