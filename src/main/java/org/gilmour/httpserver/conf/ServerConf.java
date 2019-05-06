package org.gilmour.httpserver.conf;

public class ServerConf {
    private static String webRoot;

    public static void setWebRoot(String webRoot) {
        ServerConf.webRoot = webRoot;
    }

    public static String getWebRoot() {
        return webRoot;
    }
}
