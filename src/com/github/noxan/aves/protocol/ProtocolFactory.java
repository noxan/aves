package com.github.noxan.aves.protocol;

import java.io.InputStream;
import java.io.OutputStream;

public interface ProtocolFactory {
    public InputProtocol createInputProtocol(InputStream in);

    public OutputProtocol createOutputProtocol(OutputStream out);
}
