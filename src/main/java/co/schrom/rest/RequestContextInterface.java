package co.schrom.rest;

import java.net.Socket;

public interface RequestContextInterface {
    Socket getSocket();

    void handleSocket();

    HttpRequestInterface getRequest();

    HttpResponseInterface getResponse();
}
