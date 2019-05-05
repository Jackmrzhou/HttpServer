package org.gilmour.httpserver.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class HttpBody {
    private byte[] data;

    public HttpBody(byte[] data){
        this.data = data;
    }

    public HttpBody(String body){
        this.data = body.getBytes();
    }

    public byte[] getBytes() {
        return Arrays.copyOf(data, data.length);
    }

    public InputStream getStream(){
        return new ByteArrayInputStream(data);
    }

    @Override
    public String toString() {
        return new String(data);
    }
}
