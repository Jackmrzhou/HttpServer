package org.gilmour.httpserver.dispatcher;

import org.apache.commons.io.IOUtils;
import org.gilmour.httpserver.conf.ServerConf;
import org.gilmour.httpserver.models.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticHttpDispatcher implements IHttpDispatcher {

    private IHttpDispatcher next;

    @Override
    public void dispatch(HttpContext context) throws Exception {
        HttpRequest request = context.getRequest();
        if (!request.getHeader().Method().equals("GET")){
            if (next == null){
                throw new Exception("no suitable dispatcher found");
            }
            next.dispatch(context);
        }else {
            String url = context.getRequest().getHeader().URL();
            Path path = Paths.get(ServerConf.getWebRoot(), url);
            File file = path.toFile();
            HttpResponse response = new HttpResponse();
            // build header
            HttpResponseHeaderBuilder builder = new HttpResponseHeaderBuilder();
            builder.setHttpVersion("HTTP/1.1");
            builder.setStatusCode("200");
            builder.setReasonPhrase("OK");
            builder.setField("Content-Length", String.valueOf(file.length()));
            response.setHeader(builder.build());
            //build body
            HttpBody body = new HttpBody(IOUtils.toByteArray(file.toURI()));
            response.setBody(body);

            //set response
            context.setResponse(response);
        }
    }

    @Override
    public void setNext(IHttpDispatcher next) {
        this.next = next;
    }
}
