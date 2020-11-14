package co.schrom.rest;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestContext implements RequestContextInterface {
    @Getter
    Socket socket;

    @Getter
    HttpRequest request;

    @Getter
    HttpResponse response;

    public RequestContext(Socket socket) {
        this.socket = socket;
        request = new HttpRequest();
    }

    @Override
    public void handleSocket() {
        try {
            request.read(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            // TODO: Find the invoked class
            // TODO: Create response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
