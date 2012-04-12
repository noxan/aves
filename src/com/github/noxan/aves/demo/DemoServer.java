package com.github.noxan.aves.demo;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.server.ServerHandler;
import com.github.noxan.aves.server.SocketServer;

public class DemoServer implements ServerHandler {
    public static void main(String[] args) {
	new DemoServer();
    }

    private SocketServer server;

    public DemoServer() {
	server = new SocketServer(this);
	server.start();
    }

    @Override
    public void readData(Connection connection, Object data) {
	System.out.println(connection + ": " + data);
    }

    @Override
    public void clientConnect(Connection connection) {
	System.out.println("Client connect: " + connection);
    }
}
