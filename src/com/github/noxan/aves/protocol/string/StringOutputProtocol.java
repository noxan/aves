package com.github.noxan.aves.protocol.string;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class StringOutputProtocol {
    private BufferedWriter out;

    public StringOutputProtocol(OutputStream out) {
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void write(Object data) throws IOException {
        String str = data.toString();
        if(!str.endsWith("\n")) {
            str += "\n";
        }
        out.write(str);
        out.flush();
    }
}
