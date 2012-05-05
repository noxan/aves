package com.github.noxan.aves.server;

import com.github.noxan.aves.net.Connection;

public class ServerAdapter implements ServerHandler {
    @Override
    public void readData(Connection connection, Object data) {
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
}
