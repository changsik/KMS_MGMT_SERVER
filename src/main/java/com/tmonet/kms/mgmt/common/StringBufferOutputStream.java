package com.tmonet.kms.mgmt.common;

import java.io.IOException;
import java.io.OutputStream;

public class StringBufferOutputStream extends OutputStream {
    // the target buffer
    protected StringBuffer buffer;
    public StringBufferOutputStream(StringBuffer out) {
        buffer = out;
    }
    public void write(int ch) throws IOException {
        // just append the character
        buffer.append((char)ch);
    }
}
