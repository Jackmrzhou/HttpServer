package org.gilmour.httpserver.models;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private Map<String, String> fields;

    public HttpRequestHeader(Map<String, String> fields){
        this.fields = fields;
    }

    public HttpRequestHeader() {
        fields = new HashMap<>();
    }

    public String get(String key){
        return fields.get(key);
    }

    public void set(String key, String val){
        fields.put(key, val);
    }

    public String Method(){
        return fields.get("method");
    }

    public String URL(){
        return fields.get("URL");
    }

    public String Protocol(){
        return fields.get("protocol");
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}
