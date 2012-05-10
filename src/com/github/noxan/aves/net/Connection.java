/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.net;

import java.io.IOException;

public interface Connection {
    public String getHost();

    public int getPort();

    public void disconnect();

    public void write(Object data) throws IOException;
}
