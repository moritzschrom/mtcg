package co.schrom.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    HttpRequest httpRequest;

    @BeforeEach
    void beforeEach() {
        httpRequest = new HttpRequest();
    }

    @SneakyThrows
    @Test
    @DisplayName("Initialize the request with a BufferedReader")
    void testReadInputStream() {
        // arrange
        BufferedReader reader = new BufferedReader(new StringReader("POST /messages?param=value HTTP/1.1\n" +
                "Host: localhost\n" +
                "Key: Value\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "\n" +
                "My Message\n" +
                "Second Line"));

        // act
        httpRequest.read(reader);

        // assert
        assertEquals("POST", httpRequest.getMethod());

        assertEquals("/messages", httpRequest.getPath()); // Parameters should be removed

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("param", "value");
        assertEquals(expectedParams, httpRequest.getParams());

        assertEquals("HTTP/1.1", httpRequest.getVersion());

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "localhost");
        expectedHeaders.put("Key", "Value");
        expectedHeaders.put("Content-Type", "text/html; charset=UTF-8");
        assertEquals(expectedHeaders, httpRequest.getHeaders());

        assertEquals("My Message\nSecond Line\n", httpRequest.getBody());
    }

}
