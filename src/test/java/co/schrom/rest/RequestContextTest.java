package co.schrom.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestContextTest {
    @Mock
    SocketWrapper socketMock;

    RequestContext requestContext;

    @BeforeEach
    void beforeAll() {
        requestContext = new RequestContext(socketMock);
    }

    @SneakyThrows
    @Test
    @DisplayName("Handle a Socket within the RequestContext")
    void testHandleSocket() {
        // arrange
        String request = "POST /messages HTTP/1.1\n" +
                "HOST: localhost\n" +
                "Content-Type: text/plain; charset=UTF-8\n" +
                "\n" +
                "Hallo Welt";

        when(socketMock.getInputStream()).thenReturn(new ByteArrayInputStream(request.getBytes()));

        // act
        requestContext.handleSocket();

        // assert
        verify(socketMock).getOutputStream();
    }
}
