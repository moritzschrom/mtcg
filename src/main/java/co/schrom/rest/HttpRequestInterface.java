package co.schrom.rest;

import java.io.BufferedReader;
import java.util.Map;

public interface HttpRequestInterface {
    void read(BufferedReader reader);

    String getVersion();

    String getMethod();

    String getPath();

    Map<String, String> getParams();

    Map<String, String> getHeaders();

    String getBody();
}
