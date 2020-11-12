package co.schrom.rest;

import java.io.OutputStream;
import java.util.Map;

public interface HttpResponseInterface {
    void writeStream(OutputStream outputStream);

    String getVersion();

    int getStatusCode();

    String getReasonPhrase();

    Map<String, String> getHeaders();

    String getBody();
}
