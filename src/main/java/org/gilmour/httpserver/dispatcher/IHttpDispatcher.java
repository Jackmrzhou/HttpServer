package org.gilmour.httpserver.dispatcher;

import org.gilmour.httpserver.models.HttpContext;

public interface IHttpDispatcher {
    void dispatch(HttpContext context) throws Exception;
    void setNext(IHttpDispatcher httpDispatcher);
}
