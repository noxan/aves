package com.github.noxan.aves.client.socket;

import java.io.IOException;

import com.github.noxan.aves.client.Client;

public class SocketClient implements Client {
    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void connect() throws IOException {
        // TODO Auto-generated method stub

    }
}
