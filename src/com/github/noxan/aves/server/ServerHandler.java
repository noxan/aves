package com.github.noxan.aves.server;

import com.github.noxan.aves.net.Connection;

public interface ServerHandler {
    public void readData(Connection connection, Object data);
}
