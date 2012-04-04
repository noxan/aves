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

public class SocketConnection implements Connection {
    private Logger logger = Logger.getLogger(SocketConnection.class.getName());

    private Server server;
    private Socket socket;

    private StringInputProtocol in;
    private StringOutputProtocol out;

    private boolean isConnected;

    public SocketConnection(Server server, Socket socket) {
	this.server = server;
	this.socket = socket;
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
	logger.log(Level.INFO, "connection connected");
	isConnected = true;
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
			logger.log(Level.INFO, "data: "+data);
		    } else {
			isConnected = false;
			logger.log(Level.INFO, "connection disconnected");
		    }
		} catch (EOFException e) {
		    e.printStackTrace();
		    logger.log(Level.INFO, "connection dropped");
		    isConnected = false;
		} catch (IOException e) {
		    e.printStackTrace();
		    logger.log(Level.INFO, "connection disconnected");
		    isConnected = false;
		}
	    }
	}
    }
}
