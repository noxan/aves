package com.github.noxan.aves.server;

import com.github.noxan.aves.net.Connection;

public interface Server {
    public String getHost();

    public int getPort();

    public void start();

    void offerData(Connection connection, Object data);
}
