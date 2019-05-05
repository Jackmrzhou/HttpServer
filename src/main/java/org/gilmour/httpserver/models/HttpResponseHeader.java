package org.gilmour.httpserver.models;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HttpResponseHeader {

    private byte[] data;

    public HttpResponseHeader(byte[] data){
        this.data = data;
    }

    public byte[] getBytes(){
        return null;
    }

    public InputStream getStream(){
        return new ByteArrayInputStream(data);
    }
}
