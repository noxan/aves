package com.github.noxan.aves.client;

public interface ClientHandler {
    public void clientConnect();

    public void clientDisconnect();

    public void readData(Object data);

    public void serverDisconnect();
}
