/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.protocol.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.noxan.aves.protocol.InputProtocol;

public class StringInputProtocol implements InputProtocol {
    private BufferedReader in;

    public StringInputProtocol(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public Object read() throws IOException {
        return in.readLine();
    }
}
