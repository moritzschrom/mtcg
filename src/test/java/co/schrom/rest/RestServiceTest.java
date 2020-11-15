package co.schrom.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestServiceTest {
    RestService restService;

    @BeforeEach
    void beforeEach() {
        restService = RestService.getInstance();
    }

    @Test
    @DisplayName("The RestService class should implement the singleton pattern.")
    void testRestService__singleton() {
        // act
        RestService restService2 = RestService.getInstance();

        // assert
        assertEquals(restService, restService2);
    }
}
