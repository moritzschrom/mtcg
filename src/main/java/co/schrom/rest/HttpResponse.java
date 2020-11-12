package co.schrom.rest;

import java.io.OutputStream;
import java.util.Map;

public class HttpResponse implements HttpResponseInterface {
    @Override
    public void writeStream(OutputStream outputStream) {

    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

    @Override
    public String getReasonPhrase() {
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
