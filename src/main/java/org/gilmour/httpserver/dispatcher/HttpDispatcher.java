package org.gilmour.httpserver.dispatcher;

import org.gilmour.httpserver.conf.ServerConf;
import org.gilmour.httpserver.models.*;
import org.gilmour.httpserver.views.NotFoundView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpDispatcher{
    private Logger logger = LoggerFactory.getLogger(HttpDispatcher.class);
    private IHttpDispatcher dispatcherChain;

    public HttpDispatcher(){
        dispatcherChain = new StaticHttpDispatcher();
        dispatcherChain.setNext(new DynamicHttpDispatcher());
    }

    public void dispatch(HttpContext context) throws Exception {
        if (!checkResource(context)){
            context.setResponse(new NotFoundView().toResponse());
            return;
        }
        dispatcherChain.dispatch(context);
        if(context.getResponse() == null){
            context.setResponse(new NotFoundView().toResponse());
        }
    }

    private boolean checkResource(HttpContext context){
        String url = context.getRequest().getHeader().URL();
        Path path = Paths.get(ServerConf.getWebRoot(), url);
        logger.debug(path.toString());
        File file = path.toFile();
        logger.debug(String.valueOf(file.exists()));
        return file.exists();
    }
}
