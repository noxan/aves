package com.github.noxan.aves.server;

import java.net.ServerSocket;

public class SocketServer implements Server {
    private String host;
    private int port;

    private ServerSocket server;

    public SocketServer(String host, int port) {
	this.host = host;
	this.port = port;
    }
    }
}
