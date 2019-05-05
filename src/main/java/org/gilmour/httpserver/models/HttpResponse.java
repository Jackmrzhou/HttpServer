package org.gilmour.httpserver.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

public class HttpResponse {
    private HttpResponseHeader header;
    private HttpBody body;



    public InputStream getStream() throws IOException {
        InputStream inputStream = new SequenceInputStream(header.getStream(), body.getStream());
        return inputStream;
    }

    public void setHeader(HttpResponseHeader header) {
        this.header = header;
    }

    public void setBody(HttpBody body) {
        this.body = body;
    }
}
