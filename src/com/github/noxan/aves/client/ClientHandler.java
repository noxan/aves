package com.github.noxan.aves.client;

public interface ClientHandler {
    public void clientConnect(Client client);

    public void clientDisconnect(Client client);

    public void readData(Client client, Object data);

    public void serverDisconnect(Client client);

    public void serverLost(Client client);
}
