package com.github.noxan.aves.net;

import java.net.InetAddress;

public interface Connection {
    public InetAddress getHost();

    public int getPort();
}
