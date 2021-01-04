package co.schrom.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface SocketWrapperInterface {

    Socket getSocket();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

}
