package co.schrom.rest;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestTest {
    HttpRequest httpRequest;

    @BeforeEach
    void beforeEach() {
        httpRequest = new HttpRequest();
    }

    @SneakyThrows
    @Test
    @DisplayName("Initialize the request with an InputStream")
    void testReadInputStream() {
        // arrange
        InputStream inputStream = IOUtils.toInputStream("POST /messages?param=value HTTP/1.1\n" +
                "Host: localhost\n" +
                "Key: Value\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "\n" +
                "My Message", "UTF-8");

        // act
        httpRequest.readInputStream(inputStream);

        // assert
        assertEquals(httpRequest.getMethod(), "POST");

        assertEquals(httpRequest.getPath(), "/messages"); // Parameters should be removed

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("param", "value");
        assertEquals(httpRequest.getParams(), expectedParams);

        assertEquals(httpRequest.getVersion(), "HTTP/1.1");

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "localhost");
        expectedHeaders.put("Key", "Value");
        expectedHeaders.put("Content-Type", "text/html; charset=UTF-8");
        assertEquals(httpRequest.getHeaders(), expectedHeaders);

        assertEquals(httpRequest.getBody(), "My Message");
    }

}
