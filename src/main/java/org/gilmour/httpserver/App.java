package org.gilmour.httpserver;

import org.gilmour.httpserver.service.HttpServer;
import org.gilmour.httpserver.service.IHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App
{

    private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        IHttpServer httpServer = new HttpServer();
        try {
            httpServer.Serve(8888);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("Server exiting...");
    }
}
