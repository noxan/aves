package com.github.noxan.aves.net;

import java.net.InetAddress;
import java.net.Socket;

import com.github.noxan.aves.server.Server;

public class SocketConnection implements Connection {
    private Server server;
    private Socket socket;

    public SocketConnection(Server server, Socket socket) {
	this.server = server;
	this.socket = socket;
    }

    @Override
    public void start() {

    }

    @Override
    public InetAddress getHost() {
	return socket.getInetAddress();
    }

    @Override
    public int getPort() {
	return socket.getPort();
    }
}
