package com.github.noxan.aves.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class SocketServer implements Server, Runnable {
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
	try {
	    server = new ServerSocket();
	    server.bind(new InetSocketAddress(host, port));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void run() {
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
