package org.gilmour.httpserver.dispatcher;

import org.gilmour.httpserver.adapter.PHPAdapter;
import org.gilmour.httpserver.models.HttpContext;
import org.gilmour.httpserver.models.HttpResponse;

public class DynamicHttpDispatcher implements IHttpDispatcher{
    private IHttpDispatcher next;
    private PHPAdapter adapter;

    public DynamicHttpDispatcher(){
        this.adapter = new PHPAdapter();
    }

    @Override
    public void dispatch(HttpContext context) throws Exception {
        HttpResponse response = adapter.send(context);
        context.setResponse(response);
    }

    @Override
    public void setNext(IHttpDispatcher next) {
        this.next = next;
    }
}
