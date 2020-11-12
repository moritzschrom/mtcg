package co.schrom.rest;

import lombok.Getter;

import java.io.BufferedWriter;
import java.util.Map;

public class HttpResponse implements HttpResponseInterface {
    @Getter
    String version;

    @Getter
    int statusCode;

    @Getter
    String reasonPhrase;

    @Getter
    Map<String, String> headers;

    @Getter
    String body;

    @Override
    public void write(BufferedWriter writer) {

    }
}
