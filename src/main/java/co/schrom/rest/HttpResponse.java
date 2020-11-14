package co.schrom.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
public class HttpResponse implements HttpResponseInterface {
    @Getter
    @Builder.Default
    String version = "HTTP/1.1";

    @Getter
    int statusCode;

    @Getter
    String reasonPhrase;

    @Getter
    @Singular
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
            if (body != null && body.length() > 0) {
                sb.append("\n").append(body).append("\n");
            }

            // Remove last \n
            sb.setLength(sb.length() - 1);

            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse notImplemented() {
        return HttpResponse.builder()
                .statusCode(501)
                .reasonPhrase("Not Implemented")
                .build();
    }

    public static HttpResponse ok() {
        return HttpResponse.builder()
                .statusCode(200)
                .reasonPhrase("OK")
                .build();
    }
}
