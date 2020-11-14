package co.schrom.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HttpResponseTest {
    HttpResponse response;

    @BeforeEach
    void beforeEach() {
        response = new HttpResponse();
    }

    @Test
    @DisplayName("Test default version")
    void testConstructor__defaultVersion() {
        assertEquals("HTTP/1.1", response.getVersion());
    }

    @Test
    @DisplayName("Test default status code")
    void testConstructor__defaultStatusCode() {
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("Test default reason phrase")
    void testConstructor__defaultReasonPhrase() {
        assertEquals("OK", response.getReasonPhrase());
    }

    @SneakyThrows
    @Test
    @DisplayName("Write the response to a BufferedWriter")
    void testWrite() {
        // arrange
        BufferedWriter writerMock = mock(BufferedWriter.class);

        response.version = "HTTP/1.1";
        response.statusCode = 200;
        response.reasonPhrase = "OK";
        response.headers.put("Accept", "text/plain");
        String body = "My message";
        response.body = body;
        response.headers.put("Content-Length", Integer.toString(body.getBytes().length));

        // act
        response.write(writerMock);

        // assert
        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "Accept: text/plain\n" +
                "Content-Length: " + body.getBytes().length + "\n" +
                "\n" +
                "My message";
        verify(writerMock).write(expectedResponse);
    }
}
