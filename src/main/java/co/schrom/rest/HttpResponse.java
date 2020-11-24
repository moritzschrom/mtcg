package co.schrom.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
public class HttpResponse implements HttpResponseInterface {
    @Getter
    @Builder.Default
    String version = "HTTP/1.1";

    @Getter
    @Builder.Default
    int statusCode = 200;

    @Getter
    @Builder.Default
    String reasonPhrase = "OK";

    @Getter
    @Builder.Default
    Map<String, String> headers = new HashMap<>();

    @Getter
    @Builder.Default
    String body = "";

    public HttpResponse() {
        this.headers = new HashMap<>();

        // Set default values
        this.version = "HTTP/1.1";
        //noinspection ConstantConditions
        this.statusCode = 200;
        this.reasonPhrase = "OK";
    }

    @Override
    public void write(BufferedWriter writer) {
        if (!headers.containsKey("Content-Length")) {
            headers.put("Content-Length", (body != null && body.length() > 0) ? Integer.toString(body.getBytes().length) : "0");
        }

        if (!headers.containsKey("Host")) {
            try {
                headers.put("Host", InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        try {
            StringBuilder sb = new StringBuilder();

            sb.append(version).append(" ").append(statusCode).append(" ").append(reasonPhrase).append("\r\n");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
            }
            sb.append("\r\n");
            if (body != null && body.length() > 0) {
                sb.append(body).append("\r\n");
            }

            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse internalServerError() {
        return HttpResponse.builder()
                .statusCode(500)
                .reasonPhrase("Internal Server Error")
                .body("Internal Server Error")
                .build();
    }

    public static HttpResponse notImplemented() {
        return HttpResponse.builder()
                .statusCode(501)
                .reasonPhrase("Not Implemented")
                .body("Not Implemented")
                .build();
    }

    public static HttpResponse notFound() {
        return HttpResponse.builder()
                .statusCode(404)
                .reasonPhrase("Not Found")
                .body("Not Found")
                .build();
    }

    public static HttpResponse ok() {
        return HttpResponse.builder()
                .statusCode(200)
                .reasonPhrase("OK")
                .body("OK")
                .build();
    }

    public static HttpResponse unauthorized() {
        return HttpResponse.builder()
                .statusCode(401)
                .reasonPhrase("Unauthorized")
                .body("Unauthorized")
                .build();
    }

    public static HttpResponse forbidden() {
        return HttpResponse.builder()
                .statusCode(403)
                .reasonPhrase("Forbidden")
                .body("Forbidden")
                .build();
    }
}
