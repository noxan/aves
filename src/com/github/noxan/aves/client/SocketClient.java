package com.github.noxan.aves.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.noxan.aves.net.Connection;
import com.github.noxan.aves.protocol.InputProtocol;
import com.github.noxan.aves.protocol.OutputProtocol;
import com.github.noxan.aves.protocol.ProtocolFactory;
import com.github.noxan.aves.protocol.string.StringProtocolFactory;

public class SocketClient implements Client, Connection {
    private String host;
    private int port;
    private Socket socket;
    private ClientHandler handler;

    private ProtocolFactory factory;
    private InputProtocol in;
    private OutputProtocol out;

    public SocketClient(ClientHandler handler) {
        this("localhost", 1666, handler, new StringProtocolFactory());
    }

    public SocketClient(String host, int port, ClientHandler handler, ProtocolFactory factory) {
        this.host = host;
        this.port = port;
        this.handler = handler;
        this.factory = factory;
    }

    @Override
    public void write(Object data) throws IOException {
        out.write(data);
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
        handler.clientConnect();
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        in = factory.createInputProtocol(socket.getInputStream());
        out = factory.createOutputProtocol(socket.getOutputStream());
    }

    @Override
    public void disconnect() {
        handler.clientDisconnect();
    }

    private class InputManager implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    Object data = in.read();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
