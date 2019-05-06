package org.gilmour.httpserver.handler;

import org.gilmour.httpserver.models.HttpBody;
import org.gilmour.httpserver.models.HttpRequestHeader;
import org.gilmour.httpserver.models.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class HttpParser {

    private Logger logger = LoggerFactory.getLogger(HttpParser.class);
    private enum ParsingState{
        Reading, PreDelimit, Delimit, PreEnding, Ending, Error
    }

    public HttpRequest Parse(InputStream inputStream) throws Exception{

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        StringBuilder sb = new StringBuilder();

        int byteVal;
        ParsingState state = ParsingState.Reading;

        loop: while (true){
            switch (state){
                case Reading:{
                    byteVal = bufferedInputStream.read();
                    if (byteVal == -1 ){
                        logger.error("Reading Error");
                        state = ParsingState.Error;
                    }else {
                        if (byteVal == '\r'){
                            state = ParsingState.PreDelimit;
                        }
                        sb.append((char) byteVal);
                    }
                }break;

                case PreDelimit:{
                   byteVal = bufferedInputStream.read();
                   if (byteVal != '\n'){
                       logger.error("PreDelimit Error");
                       state = ParsingState.Error;
                   }else {
                       state = ParsingState.Delimit;
                       sb.append('\n');
                   }
                }break;

                case Delimit:{
                    byteVal = bufferedInputStream.read();
                    if (byteVal == -1){
                        logger.error("Delimit Error");
                        state = ParsingState.Error;
                    }else if (byteVal == '\r'){
                        state = ParsingState.PreEnding;
                    }else {
                        state = ParsingState.Reading;
                        sb.append((char) byteVal);
                    }
                }break;

                case PreEnding:{
                    byteVal = bufferedInputStream.read();
                    if (byteVal != '\n'){
                        logger.error("PreEnding Error");
                        state = ParsingState.Error;
                    }else {
                        state = ParsingState.Ending;
                    }
                }break;

                case Ending:{
                    break loop;
                }

                case Error:{
                    throw new Exception("parsing error");
                }
            }
        }

        String headerStr = sb.toString();

        HttpRequestHeader header = parseHeader(headerStr);
        HttpBody body = null;
        if (header.Method().equals("POST")) {
            int content_length = Integer.parseInt(header.get("Content-Length"));
            byte[] bodyData = new byte[content_length];
            for (int i = 0; i < content_length; ++i){
                byteVal = bufferedInputStream.read();
                if (byteVal == -1){
                    throw new Exception("parsing error at : Parsing body");
                }else {
                    bodyData[i] = (byte) byteVal;
                }
            }
            body = new HttpBody(bodyData);
        }

        return new HttpRequest(header, body);
    }

    private HttpRequestHeader parseHeader(String headerStr) throws Exception{
        HttpRequestHeader result = new HttpRequestHeader();
        String line;
        boolean isFirst = true;
        Scanner scanner = new Scanner(headerStr);
        while (scanner.hasNextLine()){
            line = scanner.nextLine();
            if (isFirst){
                String[] parts = line.split(" ");
                if (parts.length != 3){
                    logger.error(line);
                    throw new Exception("Expect three parts in first line");
                }
                result.set("method", parts[0]);
                result.set("URL", parts[1]);
                result.set("protocol", parts[2]);
                isFirst = false;
                continue;
            }
            String [] parts = line.split(":[\\s]*", 2);
            result.set(parts[0], parts[1]);
        }
        return result;
    }
}
