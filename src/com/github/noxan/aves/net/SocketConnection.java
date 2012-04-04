package com.github.noxan.aves.net;

import java.net.Socket;

import com.github.noxan.aves.server.Server;

public class SocketConnection {
    private Server server;
    private Socket socket;

    public SocketConnection(Server server, Socket socket) {
	this.server = server;
	this.socket = socket;
    }
}
