package com.github.noxan.aves.protocol.packet;

public interface PacketSerializer {
    public Object serialize(Packet packet);

    public Packet deserialize(Object obj);
}
