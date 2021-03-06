package org.gilmour.httpserver.models;

public class HttpContext {
    private HttpRequest request;
    private HttpResponse response;

    public HttpContext(HttpRequest request, HttpResponse response){
        this.request = request;
        this.response = response;
    }

    public HttpContext(){
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
