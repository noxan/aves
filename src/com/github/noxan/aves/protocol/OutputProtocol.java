package com.github.noxan.aves.protocol;

import java.io.IOException;

public interface OutputProtocol {
    public void write(Object data) throws IOException;
}
