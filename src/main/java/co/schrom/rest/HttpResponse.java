package co.schrom.rest;

import lombok.Getter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
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

    public HttpResponse() {
        this.headers = new HashMap<>();

        // Set default values
        this.version = "HTTP/1.1";
        this.statusCode = 200;
        this.reasonPhrase = "OK";
    }

    @Override
    public void write(BufferedWriter writer) {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(version).append(" ").append(statusCode).append(" ").append(reasonPhrase).append("\n");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            if (body.length() > 0) {
                sb.append("\n").append(body);
            }

            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
