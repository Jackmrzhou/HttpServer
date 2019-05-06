package org.gilmour.httpserver.views;

import org.gilmour.httpserver.models.HttpBody;
import org.gilmour.httpserver.models.HttpResponse;
import org.gilmour.httpserver.models.HttpResponseHeader;

public class NotFoundView implements IView{

    @Override
    public HttpResponse toResponse() {
        HttpResponse response = new HttpResponse();
        response.setHeader(new HttpResponseHeader("HTTP/1.1 404 Not Found\r\nServer:BSServer\r\nContent-Length:14\r\n\r\n".getBytes()));
        response.setBody(new HttpBody("page not found".getBytes()));
        return response;
    }
}
