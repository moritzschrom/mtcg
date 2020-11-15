package co.schrom.rest;

import java.net.ServerSocket;

public interface RestServiceInterface {
    void listen(int port);

    void close();

    ServerSocket getListener();
}
