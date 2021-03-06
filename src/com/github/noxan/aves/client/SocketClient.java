package com.github.noxan.aves.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.protocol.InputProtocol;
import com.github.noxan.aves.protocol.OutputProtocol;
import com.github.noxan.aves.protocol.ProtocolFactory;
import com.github.noxan.aves.protocol.string.StringProtocolFactory;
import com.github.noxan.aves.util.Tuple;

public class SocketClient implements Client, Connection {
    private String host;
    private int port;
    private Socket socket;
    private ClientHandler handler;

    private boolean isConnected;

    private ProtocolFactory factory;
    private InputProtocol in;
    private OutputProtocol out;

    private Thread inputThread;
    private Thread managerThread;

    private BlockingQueue<Tuple<ClientEvent, Object>> clientEvents;

    public SocketClient(ClientHandler handler) {
        this("localhost", 1666, handler, new StringProtocolFactory());
    }

    public SocketClient(String host, int port, ClientHandler handler, ProtocolFactory factory) {
        this.host = host;
        this.port = port;
        this.handler = handler;
        this.factory = factory;
        this.clientEvents = new LinkedBlockingQueue<Tuple<ClientEvent, Object>>();
        this.isConnected = false;
    }

    @Override
    public void write(Object data) throws IOException {
        if(isConnected) {
            out.write(data);
        } else {
            throw new IllegalStateException("Client is not connected.");
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

    @Override
    public void connect() throws IOException {
        if(isConnected) {
            throw new IllegalStateException("Client is already connected.");
        } else {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            in = factory.createInputProtocol(socket.getInputStream());
            out = factory.createOutputProtocol(socket.getOutputStream());
            isConnected = true;
            inputThread = new Thread(new InputManager());
            inputThread.start();
            managerThread = new Thread(new EventManager());
            managerThread.start();
            handler.clientConnect(this);
        }
    }

    @Override
    public void disconnect() {
        if(isConnected) {
            isConnected = false;
            try {
                inputThread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                managerThread.join(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            handler.clientDisconnect(this);
        } else {
            throw new IllegalStateException("Client is not connected.");
        }
    }

    private void offerEvent(ClientEvent event, Object data) {
        clientEvents.offer(new Tuple<ClientEvent, Object>(event, data));
    }

    private class EventManager implements Runnable {
        @Override
        public void run() {
            while(isConnected || !clientEvents.isEmpty()) {
                Tuple<ClientEvent, Object> event = clientEvents.poll();
                if(event != null) {
                    switch(event.getFirst()) {
                        case DATA_READ:
                            handler.readData(SocketClient.this, event.getSecond());
                            break;
                        case SERVER_DISCONNECT:
                            handler.serverDisconnect(SocketClient.this);
                            break;
                        case SERVER_LOST:
                            handler.serverLost(SocketClient.this);
                            break;
                    }
                }
            }
        }
    }

    private class InputManager implements Runnable {
        @Override
        public void run() {
            while(isConnected) {
                try {
                    Object data = in.read();
                    if(data != null) {
                        offerEvent(ClientEvent.DATA_READ, data);
                    } else {
                        isConnected = false;
                        offerEvent(ClientEvent.SERVER_DISCONNECT, null);
                    }
                } catch(IOException e) {
                    isConnected = false;
                    offerEvent(ClientEvent.SERVER_LOST, null);
                }
            }
        }
    }
}
