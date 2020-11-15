package co.schrom.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

        Map<String, String> headers = new HashMap<>() {{
            put("Accept", "text/plain");
            put("Content-Length", Integer.toString("My message".getBytes().length));
        }};

        HttpResponse response = HttpResponse.builder()
                .version("HTTP/1.1")
                .statusCode(200)
                .reasonPhrase("OK")
                .headers(headers)
                .body("My message")
                .build();

        // act
        response.write(writerMock);

        // assert
        verify(writerMock, atLeastOnce()).newLine();
        verify(writerMock).write("HTTP/1.1 200 OK");
        verify(writerMock).write("Accept: text/plain");
        verify(writerMock).write("Content-Length: " + "My message".getBytes().length);
        verify(writerMock).write("My message");
    }

    @SneakyThrows
    @Test
    @DisplayName("Write a response without body to a BufferedWriter")
    void testWrite__noBody() {
        // arrange
        BufferedWriter writerMock = mock(BufferedWriter.class);

        HttpResponse response = HttpResponse.builder()
                .version("HTTP/1.1")
                .statusCode(200)
                .reasonPhrase("OK")
                .build();

        // act
        response.write(writerMock);

        // assert
        String expectedResponse = "HTTP/1.1 200 OK";
        verify(writerMock).write(expectedResponse);
    }

    @SneakyThrows
    @Test
    @DisplayName("Get a default NotImplemented response")
    void testNotImplemented() {
        // arrange
        BufferedWriter writerMock = mock(BufferedWriter.class);

        // act
        HttpResponse notImplemented = HttpResponse.notImplemented();
        notImplemented.write(writerMock);

        // assert
        String expectedResponse = "HTTP/1.1 501 Not Implemented";
        verify(writerMock).write(expectedResponse);
    }

    @SneakyThrows
    @Test
    @DisplayName("Get a default OK response")
    void testOK() {
        // arrange
        BufferedWriter writerMock = mock(BufferedWriter.class);

        // act
        HttpResponse ok = HttpResponse.ok();
        ok.write(writerMock);

        // assert
        String expectedResponse = "HTTP/1.1 200 OK";
        verify(writerMock).write(expectedResponse);
    }
}