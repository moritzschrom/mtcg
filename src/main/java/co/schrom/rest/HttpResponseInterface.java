package co.schrom.rest;

import java.io.BufferedWriter;
import java.util.Map;

public interface HttpResponseInterface {
    void write(BufferedWriter writer);

    String getVersion();

    int getStatusCode();

    String getReasonPhrase();

    Map<String, String> getHeaders();

    String getBody();
}
