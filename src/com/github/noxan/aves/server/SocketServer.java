package com.github.noxan.aves.server;

import java.net.ServerSocket;

public class SocketServer implements Server {
    private String host;
    private int port;

    private ServerSocket server;

    public SocketServer() {
	this.host = "0.0.0.0";
	this.port = 1666;
    }

    public SocketServer(String host, int port) {
	this.host = host;
	this.port = port;
    }

    @Override
    public void start() {
    }

    @Override
    public String getHost() {
	return host;
    }

    @Override
    public int getPort() {
	return port;
    }
}
