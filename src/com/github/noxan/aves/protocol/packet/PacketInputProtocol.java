package com.github.noxan.aves.protocol.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PacketInputProtocol {
    private BufferedReader in;
    private PacketSerializer serializer;

    public PacketInputProtocol(InputStream in, PacketSerializer serializer) {
        this.serializer = serializer;
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    public Packet read() throws IOException {
        return serializer.deserialize(in.readLine());
    }
}
