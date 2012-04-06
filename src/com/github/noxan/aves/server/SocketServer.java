package com.github.noxan.aves.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.net.SocketConnection;
import com.github.noxan.aves.util.Tuple;

public class SocketServer implements Server, Runnable {
    private String host;
    private int port;

    private BlockingQueue<Tuple<Connection, Object>> dataEvents;
    private ServerHandler handler;

    private Set<Connection> connections;

    private ServerSocket server;
    private Thread serverThread;

    private EventManager manager;
    private Thread managerThread;

    public SocketServer(ServerHandler handler) {
	this("0.0.0.0", 1666, handler);
    }

    public SocketServer(String host, int port, ServerHandler handler) {
	this.host = host;
	this.port = port;
	this.handler = handler;
	dataEvents = new LinkedBlockingQueue<>();
	connections = new HashSet<Connection>();
    }

    @Override
    public void start() {
	try {
	    server = new ServerSocket();
	    server.setSoTimeout(1000);
	    server.bind(new InetSocketAddress(host, port));
	    manager = new EventManager();
	    serverThread = new Thread(this);
	    serverThread.start();
	    managerThread = new Thread(manager);
	    managerThread.start();
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

    public void offerEvent(Connection connection, Object data) {
	dataEvents.add(new Tuple<Connection, Object>(connection, data));
    }

    private class EventManager implements Runnable {
	@Override
	public void run() {
	    while (true) {
		Tuple<Connection, Object> event = dataEvents.poll();
		if (event != null) {
		    handler.handleData(event.getFirst(), event.getSecond());
		}
	    }
	}
    }
}
