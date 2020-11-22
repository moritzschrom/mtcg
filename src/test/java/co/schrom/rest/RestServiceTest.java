package co.schrom.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class RestServiceTest {
    RestService restService1;
    RestService restService2;

    @BeforeEach
    void beforeEach() {
        restService1 = new RestService(0); // Any free port
        restService2 = new RestService(0); // Any free port
    }

    @Test
    @DisplayName("The RestService class should implement the Runnable interface.")
    void testRestService__runnable() {
        Thread t1 = new Thread(restService1);
        Thread t2 = new Thread(restService2);

        assertNull(restService1.getListener());
        assertNull(restService2.getListener());

        t1.start();
        t2.start();

        t1.interrupt();
        t2.interrupt();
    }
}
