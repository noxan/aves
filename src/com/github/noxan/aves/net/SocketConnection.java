package com.github.noxan.aves.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.github.noxan.aves.protocol.string.StringInputProtocol;
import com.github.noxan.aves.protocol.string.StringOutputProtocol;
import com.github.noxan.aves.server.Server;

public class SocketConnection implements Connection {
    private Server server;
    private Socket socket;

    private StringInputProtocol in;
    private StringOutputProtocol out;

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
