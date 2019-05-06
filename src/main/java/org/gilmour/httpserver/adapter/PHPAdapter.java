package org.gilmour.httpserver.adapter;

import org.apache.commons.io.IOUtils;
import org.gilmour.httpserver.conf.ServerConf;
import org.gilmour.httpserver.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class PHPAdapter {

    private Logger logger = LoggerFactory.getLogger(PHPAdapter.class);

    private String[] BuildCommand(HttpContext context){
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("/bin/sh");
        cmds.add("-c");
        cmds.add("echo '" + context.getRequest().getBody().toString() + "' | php-cgi");
        return cmds.toArray(new String[0]);
    }

    private String[] BuildEnv(HttpContext context){
        ArrayList<String> envs = new ArrayList<>();
        String url = context.getRequest().getHeader().URL();
        Path path = Paths.get(ServerConf.getWebRoot(), url);
        envs.add("REQUEST_METHOD=" + context.getRequest().getHeader().Method());
        envs.add("SCRIPT_FILENAME=" + path.toString());
        envs.add("REDIRECT_STATUS=CGI");
        envs.add("CONTENT_TYPE=" + context.getRequest().getHeader().get("Content-Type").split(";")[0]);
        envs.add("CONTENT_LENGTH=" + context.getRequest().getBody().getBytes().length);
        return  envs.toArray(new String[0]);
    }

    public HttpResponse send(HttpContext context) throws Exception{
        Runtime runtime = Runtime.getRuntime();

        String[] Commands = BuildCommand(context);
        String[] envs = BuildEnv(context);
        Process process = runtime.exec(Commands, envs);
        logger.debug(Arrays.toString(envs));
        logger.debug("exec php-cgi...");
        logger.debug(String.valueOf(process.getInputStream().available()));
        OutputStream outputStream = process.getOutputStream();
        outputStream.write(context.getRequest().getBody().getBytes());

        HttpResponse response = new HttpResponse();
        HttpResponseHeaderBuilder builder = new HttpResponseHeaderBuilder();
        builder.setStatusCode("200");
        builder.setHttpVersion("HTTP/1.1");
        builder.setReasonPhrase("OK");
        builder.setField("Content-Type", "text/html; charset=UTF-8");

        logger.debug("receiving data from php-cgi...");
        String[] result = new String(IOUtils.toByteArray(process.getInputStream())).split("\r\n");

        HttpBody body = new HttpBody(result[result.length-1]);
        builder.setField("Content-Length", String.valueOf(body.getBytes().length));
        response.setHeader(builder.build());
        response.setBody(body);
        logger.debug(body.toString());
        logger.debug("receiving done.");
        return response;
    }
}
