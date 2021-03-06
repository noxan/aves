/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.net.SocketConnection;
import com.github.noxan.aves.protocol.ProtocolFactory;
import com.github.noxan.aves.protocol.string.StringProtocolFactory;
import com.github.noxan.aves.util.Tuple;

public class SocketServer implements Server, Runnable {
    private ProtocolFactory factory;

    private String host;
    private int port;

    private BlockingQueue<Tuple<ServerEvent, Object>> serverEvents;
    private ServerHandler handler;

    private Set<Connection> connections;

    private boolean isRunning;

    private ServerSocket server;
    private Thread serverThread;

    private Thread managerThread;

    public SocketServer(ServerHandler handler) {
        this("0.0.0.0", 1666, handler, new StringProtocolFactory());
    }

    public SocketServer(String host, int port, ServerHandler handler, ProtocolFactory factory) {
        this.host = host;
        this.port = port;
        this.handler = handler;
        this.factory = factory;
        serverEvents = new LinkedBlockingQueue<Tuple<ServerEvent, Object>>();
        connections = new HashSet<Connection>();
        isRunning = false;
    }

    @Override
    public void start() throws IOException {
        if(isRunning) {
            throw new IllegalStateException("Server is already running");
        } else {
            isRunning = true;
            server = new ServerSocket();
            server.setSoTimeout(1000);
            server.bind(new InetSocketAddress(host, port));
            serverThread = new Thread(this);
            serverThread.start();
            managerThread = new Thread(new EventManager());
            managerThread.start();
        }
    }

    @Override
    public void stop() throws IOException {
        if(isRunning) {
            isRunning = false;
            try {
                serverThread.join(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                managerThread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            server.close();
        } else {
            throw new IllegalStateException("Server is not running.");
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                Socket socket = server.accept();
                SocketConnection connection = new SocketConnection(this, socket, factory);
                connection.start();
            } catch(SocketTimeoutException ignored) {
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Set<Connection> getConnections() {
        return Collections.unmodifiableSet(connections);
    }

    public void broadcast(Object data) throws IOException {
        for(Connection connection:connections) {
            connection.write(data);
        }
    }

    public void broadcast(Connection self, Object data) throws IOException {
        for(Connection connection:connections) {
            if(connection != self) {
                connection.write(data);
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

    public void offerEvent(ServerEvent type, Object data) {
        serverEvents.add(new Tuple<ServerEvent, Object>(type, data));
    }

    private class EventManager implements Runnable {
        @Override
        public void run() {
            while(isRunning || serverEvents.isEmpty()) {
                Tuple<ServerEvent, Object> event = serverEvents.poll();
                if(event != null) {
                    Tuple<?, ?> read;
                    switch(event.getFirst()) {
                        case DATA_READ:
                            read = (Tuple<?, ?>)event.getSecond();
                            handler.readData((Connection)read.getFirst(), read.getSecond());
                            break;
                        case CLIENT_CONNECT:
                            connections.add((Connection)event.getSecond());
                            handler.clientConnect((Connection)event.getSecond());
                            break;
                        case CLIENT_DISCONNECT:
                            handler.clientDisconnect((Connection)event.getSecond());
                            connections.remove((Connection)event.getSecond());
                            break;
                        case CLIENT_LOST:
                            handler.clientLost((Connection)event.getSecond());
                            connections.remove((Connection)event.getSecond());
                            break;
                        case DATA_WRITE:
                            read = (Tuple<?, ?>)event.getSecond();
                            handler.writeData((Connection)read.getFirst(), read.getSecond());
                            break;
                    }
                }
            }
        }
    }
}
