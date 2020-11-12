package co.schrom.rest;

import lombok.Getter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements HttpRequestInterface {
    @Getter
    String version;

    @Getter
    String method;

    @Getter
    String path;

    @Getter
    Map<String, String> params;

    @Getter
    HashMap<String, String> headers;

    @Getter
    String body;

    @Override
    public void readInputStream(InputStream inputStream) {

    }
}
