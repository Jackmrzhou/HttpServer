package org.gilmour.httpserver.service;

import org.gilmour.httpserver.handler.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer implements IHttpServer {

    private Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private ExecutorService threadPoolExecutor;

    public HttpServer(){
        threadPoolExecutor = Executors.newFixedThreadPool(4);
    }


    @Override
    public void Serve(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("start serving...");
        while (true){
            Socket socket = serverSocket.accept();
            logger.debug("accept :" + socket.toString());
            threadPoolExecutor.submit(new HttpHandler(socket));
        }
    }
}
