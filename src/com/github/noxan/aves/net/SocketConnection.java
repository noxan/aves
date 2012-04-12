package com.github.noxan.aves.net;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.noxan.aves.protocol.string.StringInputProtocol;
import com.github.noxan.aves.protocol.string.StringOutputProtocol;
import com.github.noxan.aves.server.Server;
import com.github.noxan.aves.server.ServerEvent;
import com.github.noxan.aves.util.Tuple;

public class SocketConnection implements Connection {
    private Logger logger = Logger.getLogger(SocketConnection.class.getName());

    private Server server;
    private Socket socket;

    private StringInputProtocol in;
    private StringOutputProtocol out;

    private boolean isConnected;

    private InputManager inputManager;
    private Thread inputThread;

    public SocketConnection(Server server, Socket socket) {
	this.server = server;
	this.socket = socket;
	inputManager = new InputManager();
	try {
	    in = new StringInputProtocol(socket.getInputStream());
	    out = new StringOutputProtocol(socket.getOutputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void write(Object data) throws IOException {
	out.write(data);
    }

    @Override
    public void start() {
	server.offerEvent(ServerEvent.CLIENT_CONNECT, this);
	isConnected = true;
	inputThread = new Thread(inputManager);
	inputThread.start();
    }

    @Override
    public InetAddress getHost() {
	return socket.getInetAddress();
    }

    @Override
    public int getPort() {
	return socket.getPort();
    }

    private class InputManager implements Runnable {
	@Override
	public void run() {
	    while (isConnected) {
		try {
		    Object data = in.read();
		    if (data != null) {
			server.offerEvent(ServerEvent.DATA_READ, new Tuple<Connection, Object>(SocketConnection.this, data));
		    } else {
			server.offerEvent(ServerEvent.CLIENT_DISCONNECT, SocketConnection.this);
			isConnected = false;
		    }
		} catch (EOFException e) {
		    server.offerEvent(ServerEvent.CLIENT_LOST, SocketConnection.this);
		    isConnected = false;
		} catch (IOException e) {
		    server.offerEvent(ServerEvent.CLIENT_DISCONNECT, SocketConnection.this);
		    isConnected = false;
		}
	    }
	}
    }
}
