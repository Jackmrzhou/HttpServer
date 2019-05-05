package org.gilmour.httpserver.handler;

import org.gilmour.httpserver.dispatcher.HttpDispatcher;
import org.gilmour.httpserver.models.HttpContext;
import org.gilmour.httpserver.models.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpHandler implements Runnable{

    private Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    private Socket socket;
    private HttpParser parser;
    private HttpDispatcher dispatcher;

    public HttpHandler(Socket socket){
        this.socket = socket;
        this.parser = new HttpParser();
        this.dispatcher = new HttpDispatcher();
    }

    @Override
    public void run(){
        try {
            logger.debug("start handling");
            HttpRequest request = parser.Parse(socket.getInputStream());
            logger.debug(request.toString());
            HttpContext context = new HttpContext(request, null);
            dispatcher.dispatch(context);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = context.getResponse().getStream();
            byte[] buffer = new byte[1024];
            int sz;
            logger.debug("start responding");
            while ((sz = inputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, sz);
            logger.debug("end of handler");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
