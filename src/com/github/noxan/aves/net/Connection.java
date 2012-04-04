package com.github.noxan.aves.net;

import java.io.IOException;
import java.net.InetAddress;

public interface Connection {
    public InetAddress getHost();

    public int getPort();

    public void start();

    public void write(Object data) throws IOException;
}
