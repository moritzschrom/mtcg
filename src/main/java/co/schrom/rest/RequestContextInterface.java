package co.schrom.rest;

import java.lang.reflect.Method;
import java.util.Map;

public interface RequestContextInterface {
    SocketWrapper getSocket();

    void handleSocket();

    HttpRequestInterface getRequest();

    HttpResponseInterface getResponse();

    Map<String, Method> getRoutes();

    Method resolveRoute(HttpRequestInterface request);
}
