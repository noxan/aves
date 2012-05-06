/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.server;

import com.github.noxan.aves.net.Connection;

public interface ServerHandler {
    public void readData(Connection connection, Object data);

    public void clientConnect(Connection connection);

    public void clientDisconnect(Connection connection);

    public void clientLost(Connection connection);
}
