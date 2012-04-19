package com.github.noxan.aves.protocol.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StringInputProtocol {
    private BufferedReader in;

    public StringInputProtocol(InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    public Object read() throws IOException {
        Object data = in.readLine();
        return data;
    }
}
