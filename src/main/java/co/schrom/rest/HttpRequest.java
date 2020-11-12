package co.schrom.rest;

import java.io.InputStream;
import java.util.Map;

public class HttpRequest implements HttpRequestInterface {
    @Override
    public void readStream(InputStream inputStream) {

    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public String getBody() {
        return null;
    }
}
