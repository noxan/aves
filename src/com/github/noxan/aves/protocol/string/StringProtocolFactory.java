package com.github.noxan.aves.protocol.string;

import java.io.OutputStream;

import com.github.noxan.aves.protocol.ProtocolFactory;

public class StringProtocolFactory implements ProtocolFactory<StringInputProtocol, StringOutputProtocol> {
    @Override
    public StringInputProtocol createInputProtocol(java.io.InputStream in) {
        return new StringInputProtocol(in);
    }

    @Override
    public StringOutputProtocol createOutputProtocol(OutputStream out) {
        return new StringOutputProtocol(out);
    }
}
