package com.github.noxan.aves.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.net.SocketConnection;

public class SocketServer implements Server, Runnable {
    private String host;
    private int port;

    private Set<Connection> connections;

    private ServerSocket server;
    private Thread serverThread;

    public SocketServer() {
	this("0.0.0.0", 1666);
    }

    public SocketServer(String host, int port) {
	this.host = host;
	this.port = port;
	connections = new HashSet<Connection>();
    }

    @Override
    public void start() {
	try {
	    server = new ServerSocket();
	    server.setSoTimeout(1000);
	    server.bind(new InetSocketAddress(host, port));
	    serverThread = new Thread(this);
	    serverThread.start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void run() {
	while (true) {
	    try {
		Socket socket = server.accept();
		Connection connection = new SocketConnection(this, socket);
		connections.add(connection);
		connection.start();
	    } catch (SocketTimeoutException e) {
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
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
