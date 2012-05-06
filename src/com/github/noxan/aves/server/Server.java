/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.server;

import java.io.IOException;
import java.util.Set;

import com.github.noxan.aves.net.Connection;

public interface Server {
    public String getHost();

    public int getPort();

    public void start() throws IOException;

    public void stop() throws IOException;

    public void offerEvent(ServerEvent type, Object data);

    public Set<Connection> getConnections();
}
