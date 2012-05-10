package com.github.noxan.aves.client;

import java.io.IOException;

public interface Client {
    public void connect() throws IOException;

    public void disconnect();
}
