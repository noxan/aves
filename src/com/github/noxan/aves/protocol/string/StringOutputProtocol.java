/* 
 * Copyright (c) 2012, noxan
 * See LICENSE for details.
 */

package com.github.noxan.aves.protocol.string;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.github.noxan.aves.protocol.OutputProtocol;

public class StringOutputProtocol implements OutputProtocol {
    private BufferedWriter out;

    public StringOutputProtocol(OutputStream out) {
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    @Override
    public void write(Object data) throws IOException {
        String str = data.toString();
        if(!str.endsWith("\n")) {
            str += "\n";
        }
        out.write(str);
        out.flush();
    }
}
