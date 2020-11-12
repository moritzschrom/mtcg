package co.schrom.rest;

import java.io.InputStream;
import java.util.Map;

public interface HttpRequestInterface {
    void readInputStream(InputStream inputStream);

    String getVersion();

    String getMethod();

    String getPath();

    Map<String, String> getHeaders();

    String getBody();
}
