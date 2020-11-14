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
    HttpRequest request;

    @BeforeEach
    void beforeEach() {
        request = new HttpRequest();
    }

    @SneakyThrows
    @Test
    @DisplayName("Initialize the request with a BufferedReader")
    void testRead() {
        // arrange
        BufferedReader reader = new BufferedReader(new StringReader("POST /messages?param=value HTTP/1.1\n" +
                "Host: localhost\n" +
                "Key: Value\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "\n" +
                "My Message\n" +
                "Second Line"));

        // act
        request.read(reader);

        // assert
        assertEquals("POST", request.getMethod());

        assertEquals("/messages", request.getPath()); // Parameters should be removed

        Map<String, String> expectedParams = new HashMap<>();
        expectedParams.put("param", "value");
        assertEquals(expectedParams, request.getParams());

        assertEquals("HTTP/1.1", request.getVersion());

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "localhost");
        expectedHeaders.put("Key", "Value");
        expectedHeaders.put("Content-Type", "text/html; charset=UTF-8");
        assertEquals(expectedHeaders, request.getHeaders());

        assertEquals("My Message\nSecond Line\n", request.getBody());
    }

}
