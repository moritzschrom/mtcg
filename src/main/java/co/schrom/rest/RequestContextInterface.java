package co.schrom.rest;

import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public interface RequestContextInterface {
    Socket getSocket();

    void handleSocket();

    HttpRequestInterface getRequest();

    HttpResponseInterface getResponse();

    Map<String, Method> getRoutes();

    Method resolveRoute(HttpRequestInterface request);
}
