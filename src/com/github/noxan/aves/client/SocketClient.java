package com.github.noxan.aves.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.noxan.aves.net.Connection;

public class SocketClient implements Client, Connection {
    private String host;
    private int port;
    private Socket socket;
    private ClientHandler handler;

    public SocketClient(ClientHandler handler) {
        this("localhost", 1666, handler);
    }

    public SocketClient(String host, int port, ClientHandler handler) {
        this.host = host;
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void write(Object data) throws IOException {
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
        handler.clientConnect();
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
    }

    @Override
    public void disconnect() {
        handler.clientDisconnect();
    }
}
