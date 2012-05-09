package com.github.noxan.aves.protocol.packet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PacketOutputProtocol {
    private PacketSerializer serializer;
    private BufferedWriter out;

    public PacketOutputProtocol(OutputStream out, PacketSerializer serializer) {
        this.serializer = serializer;
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void write(Packet packet) throws IOException {
        String data = packet.serialize().toString();
        out.write(data);
        out.flush();
    }
}
