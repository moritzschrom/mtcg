package co.schrom.rest;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapper implements SocketWrapperInterface {

    @Getter
    Socket socket;

    public SocketWrapper(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
}
