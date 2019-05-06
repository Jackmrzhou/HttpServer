package org.gilmour.httpserver.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeaderBuilder {
    private Logger logger = LoggerFactory.getLogger(HttpResponseHeaderBuilder.class);
    private Map<String, String> statusLine;
    private Map<String, String> fields;

    public HttpResponseHeaderBuilder(){
        statusLine = new HashMap<>();
        fields = new HashMap<>();
        fields.put("Server", "BSServer");
        fields.put("Connection", "close");
        //fields.put("Content-Type", "text/html");
    }

    public void setHttpVersion(String version){
        statusLine.put("HttpVersion", version);
    }

    public void setStatusCode(String code){
        statusLine.put("Code", code);
    }

    public void setReasonPhrase(String phrase){
        statusLine.put("Phrase", phrase);
    }

    public void setField(String key, String value){
        fields.put(key, value);
    }

    private boolean Validate(){
        return statusLine.size() == 3;
    }

    public HttpResponseHeader build(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(statusLine.get("HttpVersion"));
        stringBuilder.append(' ');
        stringBuilder.append(statusLine.get("Code"));
        stringBuilder.append(' ');
        stringBuilder.append(statusLine.get("Phrase"));
        stringBuilder.append("\r\n");
        for (Map.Entry<String, String> entry : fields.entrySet()){
            stringBuilder.append(entry.getKey());
            stringBuilder.append(':');
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("\r\n");
        logger.debug(stringBuilder.toString());
        return new HttpResponseHeader(stringBuilder.toString().getBytes());
    }
}
