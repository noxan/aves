/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.net;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.github.noxan.aves.protocol.InputProtocol;
import com.github.noxan.aves.protocol.OutputProtocol;
import com.github.noxan.aves.protocol.ProtocolFactory;
import com.github.noxan.aves.server.Server;
import com.github.noxan.aves.server.ServerEvent;
import com.github.noxan.aves.util.Tuple;

public class SocketConnection implements Connection {
    private Server server;
    private Socket socket;

    private InputProtocol in;
    private OutputProtocol out;

    private boolean isConnected;

    private InputManager inputManager;
    private Thread inputThread;

    public SocketConnection(Server server, Socket socket, ProtocolFactory factory) throws IOException {
        this.server = server;
        this.socket = socket;
        inputManager = new InputManager();
        in = factory.createInputProtocol(socket.getInputStream());
        out = factory.createOutputProtocol(socket.getOutputStream());
    }

    @Override
    public void write(Object data) throws IOException {
        if(isConnected) {
            server.offerEvent(ServerEvent.DATA_WRITE, new Tuple<Connection, Object>(this, data));
            out.write(data);
        }
    }

    public void start() {
        server.offerEvent(ServerEvent.CLIENT_CONNECT, this);
        isConnected = true;
        inputThread = new Thread(inputManager);
        inputThread.start();
    }

    @Override
    public void disconnect() {
        isConnected = false;
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            inputThread.join(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        server.offerEvent(ServerEvent.CLIENT_DISCONNECT, this);
    }

    @Override
    public String getHost() {
        return socket.getInetAddress().getHostAddress();
    }

    @Override
    public int getPort() {
        return socket.getPort();
    }

    @Override
    public String toString() {
        return "SocketConnection(" + getHost() + ":" + getPort() + ")";
    }

    private class InputManager implements Runnable {
        @Override
        public void run() {
            while(isConnected) {
                try {
                    Object data = in.read();
                    if(data != null) {
                        server.offerEvent(ServerEvent.DATA_READ, new Tuple<Connection, Object>(SocketConnection.this, data));
                    } else {
                        server.offerEvent(ServerEvent.CLIENT_DISCONNECT, SocketConnection.this);
                        isConnected = false;
                    }
                } catch(EOFException e) {
                    server.offerEvent(ServerEvent.CLIENT_LOST, SocketConnection.this);
                    isConnected = false;
                } catch(IOException e) {
                    server.offerEvent(ServerEvent.CLIENT_DISCONNECT, SocketConnection.this);
                    isConnected = false;
                }
            }
        }
    }
}
