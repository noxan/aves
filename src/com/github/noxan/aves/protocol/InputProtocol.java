package com.github.noxan.aves.protocol;

import java.io.IOException;

public interface InputProtocol {
    public Object read() throws IOException;
}
