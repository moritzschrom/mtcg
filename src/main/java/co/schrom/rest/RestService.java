package co.schrom.rest;

import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RestService implements RestServiceInterface, Runnable {
    private static RestService instance;

    @Getter
    ServerSocket listener;

    private int port;

    public RestService(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            listener = new ServerSocket(port, 5);
            System.out.println("Listening on port " + listener.getLocalPort() + "...");
            // noinspection InfiniteLoopStatement
            while (true) {
                Socket socket = listener.accept();
                Thread thread = new Thread(() -> new RequestContext(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        System.out.println("Closing on port " + listener.getLocalPort() + "...");
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        listen();
    }
}
