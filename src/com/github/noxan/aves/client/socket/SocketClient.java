package com.github.noxan.aves.client.socket;

import java.io.IOException;
import java.net.Socket;

import com.github.noxan.aves.client.Client;

public class SocketClient implements Client {
    private String host;
    private int port;
    private Socket socket;

    public SocketClient() {
        this("localhost", 1666);
    }

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
