package org.gilmour.httpserver.models;

public class HttpRequest {
    private HttpRequestHeader header;
    private HttpBody body;

    public HttpRequest(HttpRequestHeader header, HttpBody body){
        this.header = header;
        this.body = body;
    }


    public HttpRequestHeader getHeader() {
        return header;
    }

    public HttpBody getBody() {
        return body;
    }

    @Override
    public String toString() {
        if (body != null)
            return header.toString() + body.toString();
        else
            return header.toString();
    }
}
