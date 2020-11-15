package co.schrom.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RequestContextTest {
    @Mock
    SocketWrapper socketMock;

    @SneakyThrows
    @Test
    @DisplayName("Handle a Socket within the RequestContext")
    void testHandleSocket() {
        String request = "POST /messages HTTP/1.1\n" +
                "HOST: localhost\n" +
                "Content-Type: text/plain; charset=UTF-8\n" +
                "\n" +
                "Hallo Welt";

        when(socketMock.getInputStream()).thenReturn(new ByteArrayInputStream(request.getBytes()));
        when(socketMock.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        // act
        new RequestContext(socketMock);

        // assert
        verify(socketMock).getOutputStream();
    }
}
