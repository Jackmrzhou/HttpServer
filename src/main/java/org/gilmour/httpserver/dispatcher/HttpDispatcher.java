package org.gilmour.httpserver.dispatcher;

import org.gilmour.httpserver.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDispatcher {
    private Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);

    public void dispatch(HttpContext context) throws Exception {
        HttpResponse response = new HttpResponse();
        response.setHeader(new HttpResponseHeader("HTTP/1.1 200 OK\r\nServer:BSServer\r\nContent-Length:12\r\n\r\n".getBytes()));
        response.setBody(new HttpBody("Hello World!".getBytes()));
        context.setResponse(response);
    }
}
