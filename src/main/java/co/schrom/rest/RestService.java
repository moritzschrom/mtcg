package co.schrom.rest;

import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RestService implements RestServiceInterface, Runnable {
    private static RestService instance;

    @Getter
    ServerSocket listener;

    private RestService() {
    }

    public static RestService getInstance() {
        if (RestService.instance == null) {
            RestService.instance = new RestService();
        }
        return RestService.instance;
    }

    @Override
    public void listen(int port) {
        Runtime.getRuntime().addShutdownHook(new Thread(new RestService()));

        try {
            listener = new ServerSocket(port, 5);
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
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener = null;
    }
}
