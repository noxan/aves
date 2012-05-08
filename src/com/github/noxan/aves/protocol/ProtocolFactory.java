package com.github.noxan.aves.protocol;

import java.io.InputStream;
import java.io.OutputStream;

import com.github.noxan.aves.protocol.string.StringInputProtocol;
import com.github.noxan.aves.protocol.string.StringOutputProtocol;

public class ProtocolFactory {
    public ProtocolFactory() {
    }

    public StringInputProtocol createInputProtocol(InputStream in) {
        return new StringInputProtocol(in);
    }

    public StringOutputProtocol createOutputProtocol(OutputStream out) {
        return new StringOutputProtocol(out);
    }
}
